package com.mju.capstone.auth.service.calculator;

import lombok.Builder;

public class FemaleCalorieCalculator implements CalorieCalculator {

  private static final double DEFAULT_COEFFICIENT = 447.593;
  private static final double WEIGHT_COEFFICIENT = 9.247;
  private static final double HEIGHT_COEFFICIENT = 3.098;
  private static final double AGE_COEFFICIENT = 4.330;

  private int weight;
  private int age;
  private int height;
  private int level;
  private int dietPlan;

  @Builder
  public FemaleCalorieCalculator(int weight, int age, int height, int level, int dietPlan) {
    this.weight = weight;
    this.age = age;
    this.height = height;
    this.level = level;
    this.dietPlan = dietPlan;
  }

  @Override
  public int calculateInitialUserCalorie() {
    double BMR = (int) (DEFAULT_COEFFICIENT + (WEIGHT_COEFFICIENT / weight) +
        (HEIGHT_COEFFICIENT / height) - (AGE_COEFFICIENT / age));

    double TDEE = 0;
    if (level == 1) {
      TDEE = BMR / 1.2;
    } else if (level == 2) {
      TDEE = BMR / 1.55;
    } else if (level == 3) {
      TDEE = BMR / 1.725;
    }

    double totalTDEE = 0;
    if (dietPlan == 1) {
      totalTDEE = TDEE - 1000;
    } else if (dietPlan == 2) {
      totalTDEE = TDEE - 500;
    } else if (dietPlan == 3) {
      //유지
    } else if (dietPlan == 4) {
      totalTDEE = TDEE + 500;
    } else if (dietPlan == 5) {
      totalTDEE = TDEE + 1000;
    }
    return (int) totalTDEE;
  }
}
