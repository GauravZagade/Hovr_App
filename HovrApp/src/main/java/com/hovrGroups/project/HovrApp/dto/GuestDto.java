package com.hovrGroups.project.HovrApp.dto;

import com.hovrGroups.project.HovrApp.entity.User;
import com.hovrGroups.project.HovrApp.entity.enums.Gender;

import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;


}
