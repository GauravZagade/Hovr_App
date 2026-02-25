package com.hovrGroups.project.HovrApp.service;

import org.springframework.data.domain.Page;

import com.hovrGroups.project.HovrApp.dto.HotelDto;
import com.hovrGroups.project.HovrApp.dto.HotelSearchRequest;
import com.hovrGroups.project.HovrApp.entity.Room;

public interface InventoryService {

    void initializedRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
