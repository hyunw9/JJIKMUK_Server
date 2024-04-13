package com.mju.capstone.auth.token.dto;

import lombok.Builder;

public record TokenDto(
     String grantType,
     String accessToken,
     Long accessTokenExpiresIn,
     String refreshToken

) {

  @Builder
  public TokenDto(String grantType, String accessToken, Long accessTokenExpiresIn,
      String refreshToken) {
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    this.refreshToken = refreshToken;
  }
}
