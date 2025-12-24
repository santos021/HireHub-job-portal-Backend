package com.hirehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.ApplicationStatusRequest;
import com.hirehub.entity.Job;
import com.hirehub.entity.JobApplication;
import com.hirehub.entity.User;
import com.hirehub.repository.JobApplicationRepository;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employer/applications")
@PreAuthorize("hasRole('EMPLOYER')")
@RequiredArgsConstructor
public class EmployerApplicationController {

	private final JobRepository jobRepository;
	private final JobApplicationRepository applicationRepository;
	private final UserRepository userRepository;

	// Helper method to get logged-in employer
	private User getCurrentEmployer(Principal principal) {
		return userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("Employer not found"));
	}

	// =====================
    // Get applicants for a specific job
    // =====================
	@GetMapping("/{jobId}")
	public List<JobApplication> getApplicants(@PathVariable Long jobId, Principal principal) {

		User employer = getCurrentEmployer(principal);

		Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

		// Ensure employer owns this job
		if (!job.getPostedBy().getId().equals(employer.getId())) {
			throw new RuntimeException("You are not allowed to view applicants for this job");
		}

		return applicationRepository.findByJob(job);
	}

	// =====================
    // Update application status
    // =====================
	@PutMapping("/{applicationId}/status")
	public String updateStatus(@PathVariable Long applicationId, @RequestBody ApplicationStatusRequest request,
			Principal principal) {

		JobApplication application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found"));

		// Ensure employer owns this job
		User employer = getCurrentEmployer(principal);
		if (!application.getJob().getPostedBy().getId().equals(employer.getId())) {
			throw new RuntimeException("You are not allowed to update this application");
		}

		// Update status
		application.setStatus(request.getStatus());
		applicationRepository.save(application);

		return "Application status updated!";
	}
	
	// =====================
    // Recent Applicants (Top 5)
    // =====================
	@GetMapping("/recent")
	public List<JobApplication> getRecentApplicants(Principal principal) {
		User employer = getCurrentEmployer(principal);
		
		return applicationRepository.findTop5ByJob_PostedByOrderByAppliedAtDesc(employer);
	}
}
