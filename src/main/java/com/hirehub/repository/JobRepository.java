package com.hirehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirehub.entity.Job;
import com.hirehub.entity.JobStatus;
import com.hirehub.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {
	
	// ================= Employer =================

    // All jobs posted by an employer
    List<Job> findByPostedBy(User employer);

    // Count all jobs posted by an employer
    long countByPostedBy(User employer);

    // Count jobs by employer + status
    long countByPostedByAndStatus(User employer, JobStatus status);


    // ================= Admin =================

    // Jobs by status (PENDING / APPROVED / REJECTED)
    List<Job> findByStatus(JobStatus status);

    // Count jobs by status (for dashboard)
    long countByStatus(JobStatus status);
}
