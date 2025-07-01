package com.hovrGroups.project.HovrApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

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
                        .closed(false)
                        .build();
            
            inventoryRepository.save(inventory);

        }
    }

    @Override
    public void deleteFutureInventories(Room room) {
      LocalDate today= LocalDate.now();
      inventoryRepository.deleteByDateAfterAndRoom(today, room);
    }

}
