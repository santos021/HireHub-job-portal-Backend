package com.hirehub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.entity.Job;
import com.hirehub.entity.JobStatus;
import com.hirehub.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobSeekerJobController {
	
	private final JobRepository jobRepository;
	
	// Get only approved jobs for job seekers
	@GetMapping("/approved")
	public List<Job> getApprovedJobs(){
		return jobRepository.findByStatus(JobStatus.APPROVED);
	}
	
	 // Get job by ID
	@GetMapping("/{id}")
	public Job getJobById(@PathVariable Long id) {
		return jobRepository.findById(id)
				.filter(job -> job.getStatus() == JobStatus.APPROVED)
				.orElseThrow(() -> new RuntimeException("Job not found or not approved!"));
	}
	
	@GetMapping("/search")
	public List<Job> searchJobs(@RequestParam String keyword){
		return jobRepository.findByStatus(JobStatus.APPROVED)
				.stream()
				.filter(job -> job.getTitle().toLowerCase().contains(keyword.toLowerCase())
						|| job.getSkills().toLowerCase().contains(keyword.toLowerCase())
						|| job.getLocation().toLowerCase().contains(keyword.toLowerCase()))
				.toList();
	}
}
