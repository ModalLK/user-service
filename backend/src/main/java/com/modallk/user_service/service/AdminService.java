package com.modallk.user_service.service;

import com.modallk.user_service.dto.UpdateRoleRequest;
import com.modallk.user_service.dto.UserResponse;
import com.modallk.user_service.entity.Role;
import com.modallk.user_service.entity.User;
import com.modallk.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToUserResponse(user);
    }

    public UserResponse updateUserRole(Long id, UpdateRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        Role newRole = request.getRole().equalsIgnoreCase("ADMIN")
                ? Role.ROLE_ADMIN
                : Role.ROLE_USER;

        user.setRole(newRole);
        userRepository.save(user);
        return mapToUserResponse(user);
    }


}
