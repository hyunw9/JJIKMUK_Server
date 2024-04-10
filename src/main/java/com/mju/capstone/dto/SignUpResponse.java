package com.mju.capstone.dto;

import lombok.Builder;

public class SignUpResponse {

  boolean isSuccess;
  String message;

  @Builder
  private SignUpResponse(boolean isSuccess, String message) {
    this.isSuccess = isSuccess;
    this.message = message;
  }

  public static SignUpResponse createSignUpResponse(boolean isSuccess, String message) {
    return new SignUpResponse(isSuccess, message);
  }
}
