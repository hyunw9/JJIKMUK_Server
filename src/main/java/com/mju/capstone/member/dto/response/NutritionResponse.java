package com.mju.capstone.member.dto.response;

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
