package com.hirehub.service;

import java.util.List;

import com.hirehub.dto.AdminUserResponse;

public interface AdminUserService {
	List<AdminUserResponse> getAllUsersForAdmin();
	
	AdminUserResponse getUserById(Long id);
	
	void updateUserStatus(Long userId, boolean active);
}
