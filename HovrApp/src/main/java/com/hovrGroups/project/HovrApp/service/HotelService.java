package com.hovrGroups.project.HovrApp.service;

import com.hovrGroups.project.HovrApp.dto.HotelDto;
import com.hovrGroups.project.HovrApp.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);
}
