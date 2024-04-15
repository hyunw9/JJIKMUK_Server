package com.mju.capstone.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.auth.exception.AlreadyRegisteredException;
import com.mju.capstone.global.response.ApiError;
import com.mju.capstone.global.response.ControllerMessage;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {


  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult bindingResult = exception.getBindingResult();
    StringBuilder errMessage = new StringBuilder();

    for (FieldError error : bindingResult.getFieldErrors()) {
      errMessage.append("[")
          .append(error.getField())
          .append("] ")
          .append(":")
          .append(error.getDefaultMessage());
    }
    return new ResponseEntity<>(errMessage.toString(), HttpStatus.BAD_REQUEST);
  }



//  @ExceptionHandler(AlreadyRegisteredException.class)
//  public ResponseEntity<?> handleAlreadyRegisteredException(AlreadyRegisteredException e) {
//    Map<String, Object> response = new HashMap<>();
//    response.put("message", e.getMessage());
//    response.put("error", "Already Registered");
//    response.put("status", HttpStatus.CONFLICT.value());
//
//    // 상태 코드 409(CONFLICT)와 함께 응답
//    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAllException(Exception ex) {
    Map<String,String> localizedMessage = new HashMap<>();

    localizedMessage.put("localizedMessage",ex.getLocalizedMessage());
    ControllerMessage apiError = ControllerMessage.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .data(localizedMessage)
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}



