package com.mju.capstone.member.dto.response;

public record GoalNutritionResponse(

    String userName,
    int kcal,
    int carbohydrate,
    int protein,
    int fat
) {
  public static GoalNutritionResponse from(String userName, int kcal, int carbohydrate, int protein, int fat){
    return new GoalNutritionResponse(userName, kcal, carbohydrate, protein, fat);
  }
}
