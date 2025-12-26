package com.hirehub.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String companyName;
	private String description;
	private String salary;
	private String location;
	private String skills;
	private String experienceLevel;
	private String jobType;
	
//	private boolean approved = false; // admin approval
	@Enumerated(EnumType.STRING)
	private JobStatus status = JobStatus.PENDING;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
//	Employer reference
	@ManyToOne
	@JoinColumn(name = "posted_by")
	private User postedBy;
}