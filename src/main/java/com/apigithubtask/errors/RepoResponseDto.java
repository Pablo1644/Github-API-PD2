package com.apigithubtask.errors;

import org.springframework.http.HttpStatus;

public record RepoResponseDto(String message, HttpStatus status) {
}
