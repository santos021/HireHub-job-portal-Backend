package com.hirehub.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.hirehub.entity.User;
import com.hirehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobseeker/resume")
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequiredArgsConstructor
public class ResumeController {
	
	private final UserRepository userRepository;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	
	@PostMapping("/upload")
	public String uploadResume(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		
		
		if (file.isEmpty()) {
			return "Resume file is empty!";
		}
		
		if (!file.getOriginalFilename().endsWith(".pdf")) {
			return "Only PDF files are allowed!";
		}
		
		User user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		// Use PATH from application.properties and save inside project folder
        String path = System.getProperty("user.dir") + "/" + uploadDir + "/";
        File directory = new File(path);
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		// Unique resume filename
		String fileName = "resume_" + user.getId() + ".pdf";
		
		// Save file to server "dest" - destination
		File dest = new File(path + fileName);
		file.transferTo(dest);
		
		 // Save file path into user
		user.setResumePath(path + fileName);
		userRepository.save(user);
		
		return "Resume upload successfully!";
	}
	
	@GetMapping("/view")
	public String viewResume(Principal principal) {
		User user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		if (user.getResumePath() == null) {
			return "No resume uploaded!";
		}
		
		return user.getResumePath();
	}
	
	@GetMapping("/download/{userId}")
	@PreAuthorize("hasAnyRole('EMPLOYER', 'USER')")
	public ResponseEntity<?> downloadResume(@PathVariable Long userId) throws IOException {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found!"));

	    if (user.getResumePath() == null) {
	        return ResponseEntity.badRequest().body("No resume uploaded for this user");
	    }

	    File file = new File(user.getResumePath());

	    if (!file.exists()) {
	        return ResponseEntity.badRequest().body("Resume file not found on server!");
	    }

	    // Read file bytes
	    byte[] pdfBytes = java.nio.file.Files.readAllBytes(file.toPath());

	    return ResponseEntity.ok()
	            .header("Content-Disposition", "inline; filename=\"resume_" + userId + ".pdf\"")
	            .header("Content-Type", "application/pdf")
	            .body(pdfBytes);
	}
	
}
