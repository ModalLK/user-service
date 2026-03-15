package com.modallk.user_service.service;

import com.modallk.user_service.dto.ChangePasswordRequest;
import com.modallk.user_service.dto.UpdateProfileRequest;
import com.modallk.user_service.dto.UserResponse;
import com.modallk.user_service.entity.User;
import com.modallk.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getMyProfile() {
        return mapToUserResponse(getCurrentUser());
    }

    public UserResponse updateMyProfile(UpdateProfileRequest request) {
        User user = getCurrentUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());        // new
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    public UserResponse updateProfileImage(String imageDataUrl) {  // new
        User user = getCurrentUser();
        user.setProfileImage(imageDataUrl);
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    public void deleteMyAccount() {
        userRepository.delete(getCurrentUser());
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

