package com.mju.capstone.member.service;

import com.mju.capstone.auth.event.RegistrationCompleteEvent;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import com.mju.capstone.member.dto.response.NutritionResponse;
import com.mju.capstone.member.entity.Goal;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.GoalRepository;
import com.mju.capstone.member.service.calculator.CalorieCalculator;
import com.mju.capstone.member.service.calculator.InitialCalorieCalculatorFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoalService {

  private final GoalRepository goalRepository;
  private final MemberService memberService;

  protected Goal findGoalWithMember(Member member) {
    return goalRepository.findByMember(member)
        .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
  }

  @EventListener
  public void initializeMemberInfo(RegistrationCompleteEvent event) {
    //칼로리 계산
    CalorieCalculator calorieCalculator = InitialCalorieCalculatorFactory.getCalorieCalculator(
        event.getMemberReq());
    String email = event.getMemberReq().email();
    Member member = memberService.findByEmail(email);

    log.info(event.getMemberReq().email());
    log.info(calorieCalculator.calculateInitialUserCalorie() + "");

    NutritionResponse goalNutrition = calorieCalculator.calculateNutrition(
        calorieCalculator.calculateInitialUserCalorie());

    Goal goal = Goal.builder()
        .member(member)
        .kcal(goalNutrition.kcal())
        .carbohydrate(goalNutrition.carbohydrate())
        .protein(goalNutrition.protein())
        .fat(goalNutrition.fat())
        .build();

    Goal savedGoal = goalRepository.save(goal);
  }

  public GoalNutritionResponse getMemberGoal() {

    String email = SecurityUtil.getLoginUserEmail();
    log.info("User Email :{}", email);

    Member member = memberService.findByEmail(email);
    Goal foundGoal = findByMember(member);
    return GoalNutritionResponse.from(member.getNickname(), foundGoal.getKcal(),
        foundGoal.getCarbohydrate(), foundGoal.getProtein(), foundGoal.getFat());
  }

  private Goal findByMember(Member member) {
    return goalRepository.findByMember(member)
        .orElseThrow(() -> new EntityNotFoundException("존재하는 유저가 없습니다."));
  }

}
