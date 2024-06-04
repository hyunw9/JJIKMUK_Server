package com.mju.capstone.recommend.dto.response;

public record SupposedNutrition(
    int kcal,
    int carbohydrate,
    int protein,
    int fat
) {

  public static SupposedNutrition of(int kcal, int carbohydrate, int protein, int fat) {
    return new SupposedNutrition(kcal, carbohydrate, protein, fat);
  }
}
