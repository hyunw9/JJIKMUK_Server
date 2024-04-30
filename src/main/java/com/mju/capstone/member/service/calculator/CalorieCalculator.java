package com.mju.capstone.member.service.calculator;

import com.mju.capstone.member.dto.response.NutritionResponse;

public interface CalorieCalculator {

  int calculateInitialUserCalorie();

  NutritionResponse calculateNutrition(int kcal);
}
