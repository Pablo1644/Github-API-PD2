package com.apigithubtask.Dto;

import org.springframework.http.HttpStatus;

public record ErrorHandler(HttpStatus status, String message) {
}
