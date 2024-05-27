package com.mju.capstone.recommend.dto.request;

public record ContentRequest(
    String content
) {
  public static ContentRequest of(String content) {
    return new ContentRequest(content);
  }
}
