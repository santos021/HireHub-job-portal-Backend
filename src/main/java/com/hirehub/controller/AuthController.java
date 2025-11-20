package com.hirehub.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.AuthRequest;
import com.hirehub.dto.AuthResponse;
import com.hirehub.dto.RegisterRequest;
import com.hirehub.security.JwtUtil;
import com.hirehub.security.UserPrincipal;
import com.hirehub.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	// -------------------- REGISTER --------------------
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest req) {
		userService.registerUser(req);
		return "Registration successfull";
	}
	
	// -------------------- LOGIN --------------------
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest req) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		String token = jwtUtil.generateToken(userPrincipal.getUsername(), userPrincipal.getRoles().stream().toList());
		
		return new AuthResponse(
				token,
				userPrincipal.getEmail(),
				userPrincipal.getRoles().stream().findFirst().orElse("ROLE_USER")
				);
	}

}
