package com.mju.capstone.food.dto.response;

public record FoodResponse(
    String name,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    String imgUrl
) {

  public static FoodResponse of(String name, int kcal, int carbohydrate, int protein, int fat, String imgUrl) {
    return new FoodResponse(name, kcal, carbohydrate, protein, fat, imgUrl);
  }

}
