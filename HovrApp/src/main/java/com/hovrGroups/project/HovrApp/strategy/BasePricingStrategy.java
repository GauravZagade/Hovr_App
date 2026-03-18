package com.hovrGroups.project.HovrApp.strategy;

import java.math.BigDecimal;



import com.hovrGroups.project.HovrApp.entity.Inventory;



public class BasePricingStrategy implements PricingStrategy{

    @Override
    public BigDecimal calculatePrice(Inventory inventory){
        return inventory.getRoom().getBasePrice();
    }

}
