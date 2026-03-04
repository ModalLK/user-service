package com.modallk.user_service.controller;

import com.modallk.user_service.dto.UpdateRoleRequest;
import com.modallk.user_service.dto.UserResponse;
import com.modallk.user_service.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.modallk.user_service.dto.UpdateRoleRequest;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin user management endpoints")
@SecurityRequirement(name = "Bearer Auth")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get all users", description = "Returns list of all registered users. ADMIN only.")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID. ADMIN only.")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update user role", description = "Change a user's role to ADMIN or USER. ADMIN only.")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(adminService.updateUserRole(id, request));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete user", description = "Permanently delete a user account. ADMIN only.")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }



}

