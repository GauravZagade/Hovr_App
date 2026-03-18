package com.hovrGroups.project.HovrApp.strategy;

import java.math.BigDecimal;


import com.hovrGroups.project.HovrApp.entity.Inventory;

public interface PricingStrategy {


    BigDecimal calculatePrice(Inventory inventory);

}
