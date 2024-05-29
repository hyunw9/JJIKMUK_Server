package com.mju.capstone.recommend.dto.response;

public record RecommendResponse(
    String name
) {
  public static RecommendResponse of(String name){
    return new RecommendResponse(name);
  }
}
