package com.hirehub.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.UserProfileDTO;
import com.hirehub.entity.User;
import com.hirehub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserProfileController {
	
	private final UserRepository userRepository;
	
	// View Profile
	@GetMapping("/profile")
	public UserProfileDTO getProfile(Principal principal) {
		User user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		UserProfileDTO dto = new UserProfileDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}
	
	// Update Profile
    @PutMapping("/update-profile")
    public String updateProfile(@RequestBody UserProfileDTO dto, Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setLocation(dto.getLocation());
        user.setSkills(dto.getSkills());
        user.setEducation(dto.getEducation());
        user.setProjects(dto.getProjects());
        user.setSummary(dto.getSummary());
        user.setLinkedin(dto.getLinkedin());
        user.setGithub(dto.getGithub());
        user.setCertifications(dto.getCertifications());
        user.setDob(dto.getDob());
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setCategory(dto.getCategory());
        user.setLanguages(dto.getLanguages());

        userRepository.save(user);

        return "Profile updated successfully!";
    }
    
    // Change Password
    @PutMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> req, Principal principal) {

        String oldPassword = req.get("oldPassword");
        String newPassword = req.get("newPassword");

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect!";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        return "Password changed successfully!";
    }
	
}
