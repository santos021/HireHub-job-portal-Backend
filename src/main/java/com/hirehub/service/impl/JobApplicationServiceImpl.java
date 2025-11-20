package com.hirehub.service.impl;

import org.springframework.stereotype.Service;

import com.hirehub.entity.Job;
import com.hirehub.entity.JobApplication;
import com.hirehub.entity.User;
import com.hirehub.repository.JobApplicationRepository;
import com.hirehub.service.JobApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService{
	
	private final JobApplicationRepository jobApplicationRepository;

	@Override
	public JobApplication applyToJob(Job job, User user) {
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJob(job);
		jobApplication.setApplicant(user);
		return jobApplicationRepository.save(jobApplication);
	}
	
	
}
