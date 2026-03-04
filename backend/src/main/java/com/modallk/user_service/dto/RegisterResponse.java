package com.modallk.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String email;
    private String role;
}
