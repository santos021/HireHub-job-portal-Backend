package com.hirehub.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.entity.Job;
import com.hirehub.entity.User;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobseeker/apply")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class JobApplicationController {
	
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final JobApplicationService jobApplicationService;
	
	@PostMapping("/{jobId}")
	public String applyToJob(@PathVariable Long jobId, Principal principal) {
		User user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found!"));
		if (!job.isApproved()) {
			throw new RuntimeException("Cannot apply to an unapproved job!");
		}
		
		jobApplicationService.applyToJob(job, user);
		
		return "Successfully applied!";
	}
}
