package com.hovrGroups.project.HovrApp.dto;

import com.hovrGroups.project.HovrApp.entity.Room;
import com.hovrGroups.project.HovrApp.entity.User;
import com.hovrGroups.project.HovrApp.entity.enums.BookingStatus;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.hovrGroups.project.HovrApp.entity.Guest;
import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.Payments;

@Data
public class BookingDto {
   
    private Long id;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;

}

