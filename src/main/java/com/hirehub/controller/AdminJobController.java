package com.hirehub.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.entity.Job;
import com.hirehub.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/jobs")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor 
public class AdminJobController {
	
	private final JobRepository jobRepository;
	
	@GetMapping
	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}
	
	@GetMapping("/pending")
	public List<Job> getPendingJobs(){
		return jobRepository.findAll()
				.stream()
				.filter(job -> !job.isApproved())
				.toList();
	}
	
	@PutMapping("/{id}/approve")
	public String approveJob(@PathVariable Long id) {
		Job job = jobRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Job not found"));
		job.setApproved(true);
		jobRepository.save(job);
		
		return "Job approved successfully!";
	}
	
	@PutMapping("/{id}/reject")
	public String rejectJob(@PathVariable Long id) {
		Job job = jobRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Job not found"));
		
		job.setApproved(false);
		jobRepository.save(job);
		
		return "Job rejected!";
	}
	
	
}
