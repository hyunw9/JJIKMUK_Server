package com.mju.capstone.global.exception;

import com.mju.capstone.global.response.message.ErrorMessage;

public class InvalidException extends BusinessException{

  public InvalidException(ErrorMessage errorMessage) {
    super(errorMessage);
  }
}
