package com.hovrGroups.project.HovrApp.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.cglib.core.Local;


import com.hovrGroups.project.HovrApp.entity.Inventory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        LocalDate today = LocalDate.now();
        if(inventory.getDate().isBefore(today.plusDays(7))){
            price = price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }



}
