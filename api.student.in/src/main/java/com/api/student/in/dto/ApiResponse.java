package com.api.student.in.dto;

import java.util.Optional;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {}
