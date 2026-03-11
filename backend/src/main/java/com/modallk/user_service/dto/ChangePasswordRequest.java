package com.modallk.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank
    private String currentPassword;

    @Size(min = 6, message = "New password must be at least 6 characters")
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
