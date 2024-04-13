package com.mju.capstone.auth.token.dto;


import lombok.Builder;

public record TokenRequest(
    String accessToken,
    String refreshToken
) {

  @Builder
  public TokenRequest(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
