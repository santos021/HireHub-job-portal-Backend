package com.hirehub.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String jobType;
    private boolean approved;
    private LocalDateTime createdAt;
    private long applicantCount;
}
