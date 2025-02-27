package com.apigithubtask;

import org.springframework.http.HttpStatus;

public record DeleteGithubRepoResponseDto(String s, HttpStatus httpStatus) {
}
