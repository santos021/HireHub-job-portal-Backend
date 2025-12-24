package com.hirehub.service;

import java.util.List;

import com.hirehub.dto.AdminDashboardStatsResponse;
import com.hirehub.dto.PendingJobResponse;

public interface AdminDashboardService {
	AdminDashboardStatsResponse getStats();
	List<PendingJobResponse> getPendingJobs();
}
