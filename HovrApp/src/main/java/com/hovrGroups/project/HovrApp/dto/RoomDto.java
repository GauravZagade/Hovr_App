package com.hovrGroups.project.HovrApp.dto;

import com.hovrGroups.project.HovrApp.entity.Hotel;
import com.hovrGroups.project.HovrApp.entity.HotelContactInfo;
import com.hovrGroups.project.HovrApp.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
