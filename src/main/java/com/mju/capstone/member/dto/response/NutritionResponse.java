package com.mju.capstone.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "목표 영양소 응답")
public record NutritionResponse(

    int kcal,
    int carbohydrate,
    int protein,
    int fat
) {

  public static NutritionResponse from(int kcal,int carbohydrate,int protein,int fat){
    return new NutritionResponse(kcal, carbohydrate, protein, fat);
  }
}
