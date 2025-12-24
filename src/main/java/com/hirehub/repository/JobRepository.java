package com.hirehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirehub.entity.Job;
import com.hirehub.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {
	
	// get all jobs by employer
	List<Job> findByPostedBy(User employer);
	
	// only approved jobs for public
	List<Job> findByApprovedTrue();
	
	long countByPostedBy(User employer);

    long countByPostedByAndApproved(User employer, boolean approved);
}
