package com.hirehub.service.impl;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirehub.dto.RegisterRequest;
import com.hirehub.entity.Role;
import com.hirehub.entity.User;
import com.hirehub.exception.EmailAlreadyExistsException;
import com.hirehub.repository.RoleRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public User registerUser(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered!");
		}

		String roleName = request.getRole().equalsIgnoreCase("EMPLOYER") ? "ROLE_EMPLOYER" : "ROLE_USER";
		
		Role role = roleRepository.findByName(roleName)
				.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
		
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(Set.of(role));
		
		return userRepository.save(user);

	}
}
