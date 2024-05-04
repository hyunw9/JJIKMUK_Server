package com.mju.capstone.global.exception;

import com.mju.capstone.global.response.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

  private final ErrorMessage errorMessage;

  public BusinessException(ErrorMessage errorMessage) {
    super(errorMessage.getMessage());
    this.errorMessage = errorMessage;
  }
}
