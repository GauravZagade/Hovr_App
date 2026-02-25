package com.hovrGroups.project.HovrApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hovrGroups.project.HovrApp.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    
}
