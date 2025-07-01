package com.hovrGroups.project.HovrApp.service;

import com.hovrGroups.project.HovrApp.dto.HotelDto;
import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.Room;
import com.hovrGroups.project.HovrApp.exception.ResourceNotFoundException;
import com.hovrGroups.project.HovrApp.repository.HotelRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:"+id));
        return modelMapper.map(hotel, HotelDto.class);
        }

    @Override
    public HotelDto updateHotelById(long id, HotelDto hotelDto) {
        // TODO Auto-generated method stub
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:"+id));
        modelMapper.map(hotelDto, hotel); 
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:"+ id));
    
                hotelRepository.deleteById(id);
                for(Room room: hotel.getRooms()) {
                    inventoryService.deleteFutureInventories(room);
                } 
        
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID:" + hotelId));
        
        hotel.setActive(true);
        
        // assuming only do it once

        for(Room room: hotel.getRooms()) {
            inventoryService.initializedRoomForAYear(room);
        }

    }




}
