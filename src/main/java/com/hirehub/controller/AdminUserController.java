package com.hirehub.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.AdminUserResponse;
import com.hirehub.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
	
	private final AdminUserService adminUserService;
	
	@GetMapping
	public List<AdminUserResponse> getAllUsers() {
		return adminUserService.getAllUsersForAdmin();
	}
	
	@GetMapping("/{id}")
	public AdminUserResponse getUser(@PathVariable Long id) {
		return adminUserService.getUserById(id);
	}
	
	@PutMapping("/{id}/status")
	public String updateStatus(@PathVariable Long id, @RequestParam boolean active) {
		adminUserService.updateUserStatus(id, active);
		return active ? "User Activated" : "User Deactivated";
	}
}
