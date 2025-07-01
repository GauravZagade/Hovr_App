package com.hovrGroups.project.HovrApp.controller;

import com.hovrGroups.project.HovrApp.dto.HotelDto;
import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("Attempting to create a new hotel with name:"+ hotelDto.getName());
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        HotelDto hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto){
        HotelDto hotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId){
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }


}
