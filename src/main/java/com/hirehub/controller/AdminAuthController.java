package com.hirehub.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {
	
	 // ----------- ADMIN -------------
	@GetMapping("/test")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminTest() {
	    return "Admin access verified!";
	}
}
