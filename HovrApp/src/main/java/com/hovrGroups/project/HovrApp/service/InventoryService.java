package com.hovrGroups.project.HovrApp.service;

import com.hovrGroups.project.HovrApp.entity.Room;

public interface InventoryService {

    void initializedRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
