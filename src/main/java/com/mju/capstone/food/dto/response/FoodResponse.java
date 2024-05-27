package com.mju.capstone.food.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FoodResponse(
    String name,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    String imgUrl,
    int amount,
    int serving
) {

  public static FoodResponse of(String name, int kcal, int carbohydrate, int protein, int fat,
      String imgUrl, int amount, int serving) {
    return new FoodResponse(name, kcal, carbohydrate, protein, fat, imgUrl, amount,serving);
  }
}
