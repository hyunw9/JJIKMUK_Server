package com.mju.capstone.food.dto.response;

public record TotalNutritionResponse(

    int totalKcal,
    int totalCarbohydrate,
    int totalProtein,
    int totalFat
) {

  public static TotalNutritionResponse of(int totalKcal, int totalCarbohydrate, int totalProtein,
      int totalFat) {
    return new TotalNutritionResponse(totalKcal, totalCarbohydrate, totalProtein, totalFat);
  }
}
