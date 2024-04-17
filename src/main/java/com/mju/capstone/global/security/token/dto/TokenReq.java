package com.mju.capstone.global.security.token.dto;


import lombok.Builder;

public record TokenReq(
    String accessToken,
    String refreshToken
) {

  @Builder
  public TokenReq(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
