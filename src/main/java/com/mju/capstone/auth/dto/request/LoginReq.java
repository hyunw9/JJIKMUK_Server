package com.mju.capstone.auth.dto.request;

import lombok.Builder;

public record LoginReq(

    String email,
    String password
) {

  @Builder
  public LoginReq(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
