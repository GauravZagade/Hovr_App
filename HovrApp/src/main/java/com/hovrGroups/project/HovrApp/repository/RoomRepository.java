package com.hovrGroups.project.HovrApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hovrGroups.project.HovrApp.entity.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {
}

