package com.hirehub.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class AdminUserResponse {

    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
    private boolean active;
    private LocalDateTime createdAt;
}
