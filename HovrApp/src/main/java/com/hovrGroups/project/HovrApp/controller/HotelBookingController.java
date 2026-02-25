package com.hovrGroups.project.HovrApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hovrGroups.project.HovrApp.dto.BookingDto;
import com.hovrGroups.project.HovrApp.dto.BookingRequest;
import com.hovrGroups.project.HovrApp.dto.GuestDto;
import com.hovrGroups.project.HovrApp.entity.Booking;
import com.hovrGroups.project.HovrApp.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;
    // Add your service dependencies here as fields
    // private final BookingService bookingService;
    @PostMapping(
        value = "/init",
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
        @RequestBody List<GuestDto> guestDtoList) {
        
        
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
    
}
