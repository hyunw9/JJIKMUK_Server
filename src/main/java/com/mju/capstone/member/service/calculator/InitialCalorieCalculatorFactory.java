package com.mju.capstone.member.service.calculator;

import com.mju.capstone.member.dto.request.NutritionCalculatorRequest;
import com.mju.capstone.member.entity.Gender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialCalorieCalculatorFactory {
  public static CalorieCalculator getCalorieCalculator(
      NutritionCalculatorRequest request){
    if(request.gender().equals(Gender.MALE)){
      log.info("MALE Calculator 생성");
      return MaleCalculator.builder()
          .birth(request.birth())
          .height(request.height())
          .weight(request.weight())
          .level(request.level())
          .dietPlan(request.dietPlan())
          .build();
    }else if(request.gender().equals(Gender.FEMALE)){
      log.info("FEMALE Calculator 생성");
      return FemaleCalculator.builder()
          .birth(request.birth())
          .height(request.height())
          .weight(request.weight())
          .level(request.level())
          .dietPlan(request.dietPlan())
          .build();
    }else{
      log.error(request.gender()+"");
      throw new IllegalArgumentException("잘못된 성별입니다.");
    }
  }

}
