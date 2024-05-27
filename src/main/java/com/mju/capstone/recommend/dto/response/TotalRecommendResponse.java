package com.mju.capstone.recommend.dto.response;

public record TotalRecommendResponse(
    String name,
    int gram,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    String imgUrl
) {

  public static TotalRecommendResponse of(String name, int gram, int kcal, int carbohydrate,
      int protein, int fat, String imgUrl) {
    return new TotalRecommendResponse(name, gram, kcal, carbohydrate, protein, fat, imgUrl);
  }
}
