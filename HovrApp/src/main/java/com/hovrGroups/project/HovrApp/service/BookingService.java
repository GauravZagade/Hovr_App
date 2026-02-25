package com.hovrGroups.project.HovrApp.service;

import java.util.List;

import com.hovrGroups.project.HovrApp.dto.BookingDto;
import com.hovrGroups.project.HovrApp.dto.BookingRequest;
import com.hovrGroups.project.HovrApp.dto.GuestDto;
import com.hovrGroups.project.HovrApp.entity.Booking;

public interface BookingService {

    BookingDto initializeBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    
 
} 
