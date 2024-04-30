package com.mju.capstone.auth.dto.response;

import lombok.Builder;

public record LoginRes (
    String grantType,
    String accessToken,
    Long accessTokenExpiresIn,
    String refreshToken
){

  @Builder
  public LoginRes(String grantType, String accessToken, Long accessTokenExpiresIn,
      String refreshToken) {
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    this.refreshToken = refreshToken;
  }
}
