package com.project.accounts.exception;

import com.project.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        Map<String,String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorsList = ex.getBindingResult().getAllErrors();

        validationErrorsList.forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String validationMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMessage);
        });

        ErrorResponseDto error = ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorTime(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest webRequest) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CustomerAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex, WebRequest webRequest) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND)
                .errorTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
