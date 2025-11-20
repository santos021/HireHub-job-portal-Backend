package com.hirehub.service;

import com.hirehub.entity.Job;
import com.hirehub.entity.JobApplication;
import com.hirehub.entity.User;

public interface JobApplicationService {
	
	JobApplication applyToJob(Job job, User user);
	
}
