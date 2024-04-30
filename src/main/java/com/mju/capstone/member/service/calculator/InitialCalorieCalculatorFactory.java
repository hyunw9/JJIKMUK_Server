package com.mju.capstone.member.service.calculator;

import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.member.entity.Gender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialCalorieCalculatorFactory {
  public static CalorieCalculator getCalorieCalculator(MemberReq memberReq){
    if(memberReq.gender().equals(Gender.MALE)){
      return MaleCalculator.builder()
          .age(memberReq.birth())
          .height(memberReq.height())
          .weight(memberReq.weight())
          .level(memberReq.level())
          .dietPlan(memberReq.dietPlan())
          .build();
    }else if(memberReq.gender().equals(Gender.FEMALE)){
      return FemaleCalculator.builder()
          .age(memberReq.birth())
          .height(memberReq.height())
          .weight(memberReq.weight())
          .level(memberReq.level())
          .dietPlan(memberReq.dietPlan())
          .build();
    }else{
      log.error(memberReq.gender()+"");
      throw new IllegalArgumentException("잘못된 성별입니다.");
    }
  }

}
