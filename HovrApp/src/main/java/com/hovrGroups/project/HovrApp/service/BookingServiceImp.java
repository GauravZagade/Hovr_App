package com.hovrGroups.project.HovrApp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hovrGroups.project.HovrApp.dto.BookingDto;
import com.hovrGroups.project.HovrApp.dto.BookingRequest;
import com.hovrGroups.project.HovrApp.dto.GuestDto;
import com.hovrGroups.project.HovrApp.entity.Booking;
import com.hovrGroups.project.HovrApp.entity.Guest;
import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.Inventory;
import com.hovrGroups.project.HovrApp.entity.Room;
import com.hovrGroups.project.HovrApp.entity.User;
import com.hovrGroups.project.HovrApp.entity.enums.BookingStatus;
import com.hovrGroups.project.HovrApp.exception.ResourceNotFoundException;
import com.hovrGroups.project.HovrApp.repository.BookingRepository;
import com.hovrGroups.project.HovrApp.repository.GuestRepository;
import com.hovrGroups.project.HovrApp.repository.HotelRepository;
import com.hovrGroups.project.HovrApp.repository.InventoryRepository;
import com.hovrGroups.project.HovrApp.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService{

    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final GuestRepository guestRepository;
    

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest) {

        log.info("initializing booking for hotel : {}, room: {}, date{}-{}", bookingRequest.getHotelId(),
            bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckInDate()
        );


        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() -> 
        new ResourceNotFoundException("Hotel not found with ID: " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> 
        new ResourceNotFoundException("Room not found with ID: " + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventories(room.getId(),
         bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());


         long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;
         if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
         }

         // Reserve the room/ update the booked countof inventiries

         for (Inventory inventory: inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
         }

         inventoryRepository.saveAll(inventoryList);

         // Create the Booking
         User user = new User();
         user.setId(1L);   // TODO: Remove Dummy User
         // TODO: Calculate dynamic amount

         Booking booking = Booking.builder()
                            .bookingStatus(BookingStatus.RESERVED)
                            .hotel(hotel)
                            .room(room)
                            .checkInDate(bookingRequest.getCheckInDate())
                            .checkOutDate(bookingRequest.getCheckOutDate())
                            .user(getCurrentUser())
                            .roomsCount(bookingRequest.getRoomsCount())
                            .amount(BigDecimal.TEN)
                            .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }



    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding Guest for booking with id: {}", bookingId);
      
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> 
        new ResourceNotFoundException("Booking  not found with ID: " + bookingId));


        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }

        for (GuestDto guestDto: guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);

    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser() {
        User user = new User();
        user.setId(1L); // Todo: Remove Dummy User
        return user;
    }

}
