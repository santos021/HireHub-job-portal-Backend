package com.hirehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.JobRequest;
import com.hirehub.dto.JobResponse;
import com.hirehub.entity.Job;
import com.hirehub.entity.JobStatus;
import com.hirehub.entity.User;
import com.hirehub.repository.JobApplicationRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employer/jobs")
@PreAuthorize("hasRole('EMPLOYER')")
@RequiredArgsConstructor
public class EmployerJobController {
	
	private final JobService jobService;
	private final UserRepository userRepository;
	private final JobApplicationRepository applicationRepository;
	
	public User getCurrentEmployer(Principal principal) {
		return userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@PostMapping
	public Job createJob(@RequestBody JobRequest request, Principal principal) {
		return jobService.createJob(request, getCurrentEmployer(principal));
	}
	
	@PutMapping("/{id}")
	public Job updateJob(@PathVariable Long id,@RequestBody JobRequest request,Principal principal) {
		return jobService.updateJob(id, request, getCurrentEmployer(principal));
	}
	
	@DeleteMapping("/{id}")
	public String deleteJob(@PathVariable Long id, Principal principal) {
		jobService.deleteJob(id, getCurrentEmployer(principal));
		return "Job Deleted successfully";
	}
	
	@GetMapping("/all-jobs")
	public List<JobResponse> getEmployerJobs(Principal principal){
		User employer = getCurrentEmployer(principal);

	    List<Job> jobs = jobService.getEmployerJobs(employer);

	    return jobs.stream().map(job -> new JobResponse(
	    				job.getId(),
	    				job.getTitle(),
	    				job.getJobType(),
	    				job.getStatus(),
	    				job.getCreatedAt(),
	    				applicationRepository.countByJob(job)
	    		)).toList();
	}
	
}
