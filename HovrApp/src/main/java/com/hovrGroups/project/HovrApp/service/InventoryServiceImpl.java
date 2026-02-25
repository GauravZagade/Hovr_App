package com.hovrGroups.project.HovrApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.hovrGroups.project.HovrApp.dto.HotelDto;
import com.hovrGroups.project.HovrApp.dto.HotelSearchRequest;
import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.Inventory;
import com.hovrGroups.project.HovrApp.entity.Room;
import com.hovrGroups.project.HovrApp.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public void initializedRoomForAYear(Room room){
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for(; !today.isAfter(endDate);today=today.plusDays(1)){
            Inventory inventory = Inventory.builder()
                        .hotel(room.getHotel())
                        .room(room)
                        .bookedCount(0)
                        .city(room.getHotel().getCity())
                        .date(today)
                        .price(room.getBasePrice())
                        .surgeFactor(BigDecimal.ONE)
                        .totalCount(room.getTotalCount())
                        .bookedCount(0)
                        .reservedCount(0)
                        .closed(false)
                        .build();
            
            inventoryRepository.save(inventory);

        }
    }

    @Override
    public void deleteAllInventories(Room  room) {
    log.info("Deleting all inventories for room: {}", room.getId());
      LocalDate today= LocalDate.now();
      inventoryRepository.deleteByRoom(room);
    }


    @Override
    public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {

        log.info("Searching for hotels with city: {}, start date: {}, end date: {}, rooms count: {}", hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate());
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());

        long dateCount = 
                ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;
        
       Page<Hotel> hotelPage =  inventoryRepository.findHotelWithAvailableInventory(hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), 
        hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(), dateCount , pageable);
        return hotelPage.map((element)-> modelMapper.map(element, HotelDto.class));
    }

    


}
