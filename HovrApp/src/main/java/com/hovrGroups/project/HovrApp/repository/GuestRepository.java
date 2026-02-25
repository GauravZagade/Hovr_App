package com.hovrGroups.project.HovrApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hovrGroups.project.HovrApp.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
