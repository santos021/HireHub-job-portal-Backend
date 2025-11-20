package com.hirehub.service;

import com.hirehub.dto.RegisterRequest;
import com.hirehub.entity.User;

public interface UserService {
	User registerUser(RegisterRequest request);
}
