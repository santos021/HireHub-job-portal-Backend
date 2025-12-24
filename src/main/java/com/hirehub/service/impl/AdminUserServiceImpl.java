package com.hirehub.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hirehub.dto.AdminUserResponse;
import com.hirehub.entity.User;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    // 🔹 View all users
    @Override
    public List<AdminUserResponse> getAllUsersForAdmin() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 🔹 View single user
    @Override
    public AdminUserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    // 🔹 Activate / Deactivate
    @Override
    public void updateUserStatus(Long id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        userRepository.save(user);
    }

    // 🔹 Mapper
    private AdminUserResponse mapToDto(User user) {
        AdminUserResponse dto = new AdminUserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRoles(
            user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
        return dto;
    }
}
