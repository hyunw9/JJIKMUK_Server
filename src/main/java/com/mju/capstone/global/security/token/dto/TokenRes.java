package com.mju.capstone.global.security.token.dto;

import lombok.Builder;

public record TokenRes(
     String grantType,
     String accessToken,
     Long accessTokenExpiresIn,
     String refreshToken

) {

  @Builder
  public TokenRes(String grantType, String accessToken, Long accessTokenExpiresIn,
      String refreshToken) {
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    this.refreshToken = refreshToken;
  }
}
