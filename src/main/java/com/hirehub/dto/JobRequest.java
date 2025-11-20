package com.hirehub.dto;

import lombok.Data;

@Data
public class JobRequest {
	private String title;
    private String companyName;
    private String description;
    private String salary;
    private String location;
    private String skills;
    private String experienceLevel;
    private String jobType;
}
