package com.hovrGroups.project.HovrApp.repository;

import com.hovrGroups.project.HovrApp.entity.Inventory;
import com.hovrGroups.project.HovrApp.entity.Room;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
