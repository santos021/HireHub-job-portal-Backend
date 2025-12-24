package com.hirehub.dto;

import java.time.LocalDateTime;

import com.hirehub.entity.User;

import lombok.Data;

@Data
public class PendingJobResponse {
	private Long id;
	private String title;
	private String companyName;
	private User postedBy;
	private LocalDateTime createdAt;
}
