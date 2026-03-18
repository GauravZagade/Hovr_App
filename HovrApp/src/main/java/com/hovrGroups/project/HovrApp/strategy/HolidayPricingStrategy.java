package com.hovrGroups.project.HovrApp.strategy;

import java.math.BigDecimal;


import com.hovrGroups.project.HovrApp.entity.Inventory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    
    @Override
    public BigDecimal calculatePrice(Inventory inventory){
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true; // call an API or check with local data

        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
