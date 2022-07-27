package com.example.demospringsecurity.model.dto;

import lombok.Data;

@Data
public class UpdateRequestDto {
    private String userName;
    private String password;
    private String role;
}
