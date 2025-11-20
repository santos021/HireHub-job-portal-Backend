package com.hirehub.service;

import java.util.List;

import com.hirehub.dto.JobRequest;
import com.hirehub.entity.Job;
import com.hirehub.entity.User;

public interface JobService {
	Job createJob(JobRequest request, User employer);
	
	Job updateJob(Long id, JobRequest request, User employer);
	
	void deleteJob(Long id, User employer);
	
	List<Job> getEmployerJobs(User employer);
}
