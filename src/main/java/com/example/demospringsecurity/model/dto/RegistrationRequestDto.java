package com.example.demospringsecurity.model.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String userName;
    private String password;
    private String role;
}
