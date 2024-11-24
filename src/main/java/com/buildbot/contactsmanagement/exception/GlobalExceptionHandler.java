package com.buildbot.contactsmanagement.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.buildbot.contactsmanagement.responseDto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest webRequest) {
        log.error("MethodArgumentNotValidException exception occurred.....", ex);

        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setApiPath(webRequest.getDescription(false));
        dto.setErrorCode(HttpStatus.BAD_REQUEST);
        dto.setErrorMessage(ex.getBindingResult().getFieldError().getField() + " : " + 
                            ex.getFieldError().getDefaultMessage());
        dto.setErrorTime(LocalDateTime.now());

        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistException(
            ResourceAlreadyExistException ex, WebRequest webRequest) {
        log.error("ResourceAlreadyExistException exception occurred.....", ex);

        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setApiPath(webRequest.getDescription(false));
        dto.setErrorCode(HttpStatus.CONFLICT);
        dto.setErrorMessage(ex.getMessage());
        dto.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest webRequest) {
        log.error("ResourceNotFoundException exception occurred.....", ex);

        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setApiPath(webRequest.getDescription(false));
        dto.setErrorCode(HttpStatus.NOT_FOUND);
        dto.setErrorMessage(ex.getMessage());
        dto.setErrorTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, WebRequest webRequest) {
        log.error("Generic exception occurred.....", ex);

        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setApiPath(webRequest.getDescription(false));
        dto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        dto.setErrorMessage(ex.getMessage());
        dto.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
