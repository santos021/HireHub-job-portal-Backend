package com.hirehub.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirehub.dto.AdminDashboardStatsResponse;
import com.hirehub.dto.PendingJobResponse;
import com.hirehub.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/stats")
    public AdminDashboardStatsResponse getStats() {
        return adminDashboardService.getStats();
    }

    @GetMapping("/pending-jobs")
    public List<PendingJobResponse> getPendingJobs() {
        return adminDashboardService.getPendingJobs();
    }
}
