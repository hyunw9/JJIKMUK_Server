package com.mju.capstone.food.exception;

import com.mju.capstone.global.exception.BusinessException;
import com.mju.capstone.global.response.message.ErrorMessage;

public class IllegalArgumentException extends BusinessException {

  public IllegalArgumentException(
      ErrorMessage errorMessage) {
    super(errorMessage);
  }
}
