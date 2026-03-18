package com.hovrGroups.project.HovrApp.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.HotelMinPrice;
import com.hovrGroups.project.HovrApp.entity.Inventory;
import com.hovrGroups.project.HovrApp.repository.HotelMinPriceRepository;
import com.hovrGroups.project.HovrApp.repository.HotelRepository;
import com.hovrGroups.project.HovrApp.repository.InventoryRepository;
import com.hovrGroups.project.HovrApp.strategy.PricingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {

    // Schedular to update the invenntory and hotel min price

    private final HotelRepository hotelRepository ;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

    // @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    public void updatePrices(){

        int page = 0;
        int batchSize = 100;

        while (true) {

            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
            if(hotelPage.isEmpty()){
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrices);

            page++;
        }
    }

    private void updateHotelPrices(Hotel hotel){
        log.info("Updating prices for hotel ID: {}", hotel.getId());
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel,starDate,endDate);
        
        updateInventoryPrice(inventoryList);

        updateHotelMinPrices(hotel, inventoryList, starDate, endDate);
    
    }

    private void updateHotelMinPrices(Hotel hotel, List<Inventory> inventoryList, LocalDate starDate, LocalDate endDate) {
            // Compute minimum price per day for the hotel
        Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
        .collect(Collectors.groupingBy(
                Inventory::getDate,
                Collectors.mapping(Inventory::getPrice, Collectors.minBy(Comparator.naturalOrder()))
                    ))
                    .entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().orElse(BigDecimal.ZERO)));

        // Prepare HotelPrice entities in bulk
        List<HotelMinPrice> hotelPrices = new ArrayList<>();
        dailyMinPrices.forEach((date, price) -> {
            HotelMinPrice hotelPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                    .orElse(new HotelMinPrice(hotel, date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);
        });

        // Save all HotelPrice entities in bulk
        hotelMinPriceRepository.saveAll(hotelPrices);
    }

    private void updateInventoryPrice(List<Inventory> inventoryList){
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });

        inventoryRepository.saveAll(inventoryList);
    }



}
