package com.hirehub.dto;

import lombok.Data;

@Data
public class ApplicationStatusRequest {
    private String status; // SELECTED / REJECTED / PENDING
}
