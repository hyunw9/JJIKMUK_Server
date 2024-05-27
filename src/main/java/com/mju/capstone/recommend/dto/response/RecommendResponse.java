package com.mju.capstone.recommend.dto.response;

public record RecommendResponse(
    String name,
    int gram,
    int kcal,
    int carbohydrate,
    int protein,
    int fat
) {
  public static RecommendResponse of(String name, int gram, int kcal, int carbohydrate, int protein,int fat){
    return new RecommendResponse(name, gram, kcal, carbohydrate, protein, fat);
  }
}
