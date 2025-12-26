package com.hirehub.dto;

import java.time.LocalDateTime;

import com.hirehub.entity.JobStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String jobType;
    private JobStatus status;
    private LocalDateTime createdAt;
    private long applicantCount;
}
