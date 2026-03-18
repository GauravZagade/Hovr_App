package com.hovrGroups.project.HovrApp.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.hovrGroups.project.HovrApp.entity.Inventory;

@Service
public class PricingService {


    public BigDecimal calculateDynamicPricing(Inventory inventory){
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        // apply the additional strategies

        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }

}
