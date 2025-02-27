package com.apigithubtask.errors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class RepoErrorHandler{
    @ExceptionHandler(RepoNotFoundException.class)
    public ResponseEntity<RepoResponseDto> handleException(RepoNotFoundException exception) {
        log.warn("SongNotFoundException while accessing song");
        RepoResponseDto errorSongResponseDto = new RepoResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorSongResponseDto);
    }
}
