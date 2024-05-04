package com.mju.capstone.global.exception;

import com.mju.capstone.global.response.message.ErrorMessage;

public class AuthException extends BusinessException {

  public AuthException(ErrorMessage errorMessage) {
    super(errorMessage);
  }
}
