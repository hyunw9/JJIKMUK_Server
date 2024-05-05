package com.mju.capstone.member.service.calculator;

import com.mju.capstone.member.dto.response.NutritionResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FemaleCalculator implements CalorieCalculator {

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
  public FemaleCalculator(int weight, int birth, int height, int level, int dietPlan) {
    this.weight = weight;
    this.age = LocalDateTime.now().getYear()-birth;
    this.height = height;
    this.level = level;
    this.dietPlan = dietPlan;
  }

  @Override
  public int calculateInitialUserCalorie() {
    double BMR =  DEFAULT_COEFFICIENT + (WEIGHT_COEFFICIENT * weight) +
        (HEIGHT_COEFFICIENT * height) - (AGE_COEFFICIENT * age);
    log.info("BMR : "+ BMR);
    log.info("level :  "+level);
    double TDEE = BMR;

    if (level == 1) {
      TDEE *= 1.2;
    } else if (level == 2) {
      TDEE *= 1.375;
    } else if (level == 3) {
      TDEE *= 1.9;
    }else if(level == 4){
      TDEE *= 1.725;
    }else if(level ==5){
      TDEE *= 1.9;
    }

    log.info("TDEE: "+TDEE);

    double totalTDEE = TDEE;
    if (dietPlan == 1) {
      totalTDEE -=1000;
    } else if (dietPlan == 2) {
      totalTDEE-= 500;
    } else if (dietPlan == 4) {
      totalTDEE += 500;
    } else if (dietPlan == 5) {
      totalTDEE += 1000;
    }
    log.info("TOTAL TDEE : "+ (int)totalTDEE);
    return (int) totalTDEE;
  }

  public NutritionResponse calculateNutrition(int kcal){

    int carbohydrate = (int)((kcal * 0.5)/4);
    int protein = (int)((kcal * 0.3)/4);
    int fat = (int)((kcal * 0.2)/9);

    return NutritionResponse.from(kcal,carbohydrate,protein,fat);
  }
}
