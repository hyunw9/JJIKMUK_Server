package com.mju.capstone.global.exception;

import com.mju.capstone.global.response.message.ErrorMessage;

public class NotFoundException extends BusinessException {

  public NotFoundException(ErrorMessage errorMessage) {
    super(errorMessage);
  }
}
