package com.mju.capstone.member.service.calculator;

import com.mju.capstone.member.dto.response.NutritionResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaleCalculator implements CalorieCalculator {

  private static final double DEFAULT_COEFFICIENT = 662;
  private static final double COEFFICIENT_1 = 9.53;
  private static final double COEFFICIENT_2 = 15.91;
  private static final double COEFFICIENT_3 = 539.6;
  private static final double[] pa = {0, 1.0, 1.13, 1.26};
  private static final int[] dietKcal = {0, -1000, -500, 0, 500, 1000};

  private final int weight;
  private final int age;
  private final double height;
  private final int level;
  private final int dietPlan;

  @Builder
  public MaleCalculator(int weight, int birth, int height, int level, int dietPlan) {
    this.weight = weight;
    this.age = LocalDateTime.now().getYear() - birth;
    this.height = (height / 100);
    this.level = level;
    this.dietPlan = dietPlan;
  }

  @Override
  public int calculateInitialUserCalorie() {
    double kcal = DEFAULT_COEFFICIENT -
        COEFFICIENT_1 * age + pa[level] * (COEFFICIENT_2 * weight + COEFFICIENT_3 * height);
    log.info("kcal: " + kcal);

    kcal += dietKcal[dietPlan];
    log.info("TOTAL kcal : " + (int) kcal);
    return (int) kcal;
  }

  public NutritionResponse calculateNutrition(int kcal) {

    int carbohydrate = (int) ((kcal * 0.6) / 4);
    int protein = (int) ((kcal * 0.1) / 4);
    int fat = (int) ((kcal * 0.3) / 9);

    return NutritionResponse.from(kcal, carbohydrate, protein, fat);
  }
}
