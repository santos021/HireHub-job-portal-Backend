package com.hirehub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hirehub.dto.AdminDashboardStatsResponse;
import com.hirehub.dto.PendingJobResponse;
import com.hirehub.entity.Job;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
	
	private final UserRepository userRepository;
	private final JobRepository jobRepository;
	
	@Override
	public AdminDashboardStatsResponse getStats() {
		long totalUsers = userRepository.count();
		long totalEmployers = userRepository.countByRoles_Name("ROLE_EMPLOYER");
		long totalJobs = jobRepository.count();
		
		
		return new AdminDashboardStatsResponse(totalUsers, totalEmployers, totalJobs);
	}
	
	@Override
	public List<PendingJobResponse> getPendingJobs() {
		
		List<Job> jobs = jobRepository.findByApprovedFalse();
		
		return jobs.stream().map(job -> {
			PendingJobResponse dto = new PendingJobResponse();
			dto.setId(job.getId());
			dto.setTitle(job.getTitle());
			dto.setCompanyName(job.getCompanyName());
			dto.setPostedBy(job.getPostedBy());
			dto.setCreatedAt(job.getCreatedAt());
			return dto;
		}).collect(Collectors.toList());
	}	
	
}
