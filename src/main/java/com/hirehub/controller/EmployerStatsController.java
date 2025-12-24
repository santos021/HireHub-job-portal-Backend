package com.hirehub.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.entity.User;
import com.hirehub.repository.JobApplicationRepository;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employer/dashboard")
@RequiredArgsConstructor
public class EmployerStatsController {
	
	private final JobRepository jobRepository;
	private final JobApplicationRepository applicationRepository;
	private final UserRepository userRepository;
	
	@GetMapping("/stats")
    public Map<String, Object> getStats(Principal principal) {

        User employer = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Long employerId = employer.getId();

        long totalJobs = jobRepository.countByPostedBy(employer);
        long activeJobs = jobRepository.countByPostedByAndApproved(employer, true);
        long totalApplications = applicationRepository.countByJob_PostedBy(employer);
        long shortlisted = applicationRepository.countByJob_PostedByAndStatus(employer, "SHORTLISTED");

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalJobs", totalJobs);
        stats.put("activeJobs", activeJobs);
        stats.put("applications", totalApplications);
        stats.put("shortlisted", shortlisted);

        return stats;
    }
}
