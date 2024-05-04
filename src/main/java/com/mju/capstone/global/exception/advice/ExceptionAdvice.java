package com.mju.capstone.global.exception.advice;

import com.mju.capstone.global.exception.AuthException;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.ControllerMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ControllerMessage> handleAuthException(AuthException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ControllerMessage.of(e.getErrorMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ControllerMessage> handleNotFoundException(NotFoundException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ControllerMessage.of(e.getErrorMessage()));
  }
}



