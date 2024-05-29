package com.mju.capstone.recommend.dto.response;

public record TotalRecommendResponse(
    String name,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    String imgUrl,
    int amount,
    int serving
) {

  public static TotalRecommendResponse of(String name, int kcal, int carbohydrate,
      int protein, int fat, String imgUrl, int amount, int serving) {
    return new TotalRecommendResponse(name, kcal, carbohydrate, protein, fat, imgUrl, amount, serving);
  }
}
