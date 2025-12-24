package com.hirehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDashboardStatsResponse {
	private long totalUsers;
	private long totalEmployers;
	private long totalJobs;
}
