package com.mju.capstone.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "목표 영양소 응답")
public record GoalNutritionResponse(

    String userName,
    int dietPlan,
    int kcal,
    int carbohydrate,
    int protein,
    int fat
) {
  public static GoalNutritionResponse from(String userName, int dietPlan, int kcal, int carbohydrate, int protein, int fat){
    return new GoalNutritionResponse(userName, dietPlan,kcal, carbohydrate, protein, fat);
  }
}
