package com.hirehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirehub.entity.Job;
import com.hirehub.entity.JobApplication;
import com.hirehub.entity.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
	
	List<JobApplication> findByApplicant(User user);
	
	List<JobApplication> findByJob(Job job);
}
