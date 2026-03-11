package com.modallk.user_service.dto;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String role; // "ADMIN" or "USER"
}
