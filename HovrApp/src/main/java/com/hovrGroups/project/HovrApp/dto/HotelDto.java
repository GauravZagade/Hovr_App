package com.hovrGroups.project.HovrApp.dto;

import com.hovrGroups.project.HovrApp.entity.HotelContactInfo;
import com.hovrGroups.project.HovrApp.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class HotelDto {

    private Long id; // Primary Key
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private Boolean active;

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }
    // public void setActive(Boolean active) {
    //     this.active = active;
    // }

}
