package com.mju.capstone.member.dto.request;

import com.mju.capstone.member.entity.Gender;
import com.mju.capstone.member.entity.Member;

public record NutritionCalculatorRequest(
    String email,
    int birth,
    int height,
    int weight,
    Gender gender,
    int level,
    int dietPlan
) {

  public static NutritionCalculatorRequest of(String email,int birth, int height, int weight,
      Gender gender, int level, int dietPlan) {
    return new NutritionCalculatorRequest(email,birth, height, weight, gender, level, dietPlan);
  }
  public static NutritionCalculatorRequest fromMember(Member member) {
    return new NutritionCalculatorRequest(
        member.getEmail(),
        member.getBirth(),
        member.getHeight(),
        member.getWeight(),
        member.getGender(),
        member.getLevel(),
        member.getDietPlan()
    );
  }
}
