package com.mju.capstone.member.service;

import com.mju.capstone.auth.event.RegistrationCompleteEvent;
import com.mju.capstone.food.entity.History;
import com.mju.capstone.food.service.HistoryService;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import com.mju.capstone.member.dto.response.NutritionResponse;
import com.mju.capstone.member.entity.Goal;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.GoalRepository;
import com.mju.capstone.member.service.calculator.CalorieCalculator;
import com.mju.capstone.member.service.calculator.InitialCalorieCalculatorFactory;
import com.mju.capstone.recommend.dto.response.SupposedNutrition;
import java.util.HashMap;
import java.util.Map;
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
  private final HistoryService historyService;
  private static final Map<String, Double> mealTimeRatios = new HashMap<>();

  static {
    mealTimeRatios.put("아침", 0.2);
    mealTimeRatios.put("점심", 0.3);
    mealTimeRatios.put("저녁", 0.5);
  }

  protected Goal findGoalWithMember(Member member) {
    return goalRepository.findByMember(member)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND));
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
    return GoalNutritionResponse.from(member.getNickname(), member.getDietPlan(),
        foundGoal.getKcal(),
        foundGoal.getCarbohydrate(), foundGoal.getProtein(), foundGoal.getFat());
  }

  private Goal findByMember(Member member) {
    return goalRepository.findByMember(member)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND));
  }

  public SupposedNutrition calculateSupposedNutrition(GoalNutritionResponse memberGoal) {
    //유저의 목표 영양소와 현재까지 먹은 영양소를 빼야한다.
    int supposedKcal = memberGoal.kcal();
    int supposedCarbohydrate = memberGoal.carbohydrate();
    int supposedProtein = memberGoal.protein();
    int supposedFat = memberGoal.fat();

    //만약 유저가 음식을 하나도 안먹었다면, History가 존재하지 않는다면
    History history = historyService.findUserHistory();
    if (history != null) {
      supposedKcal -= history.getTot_kcal();
      supposedCarbohydrate -= history.getTot_carbohydrate();
      supposedProtein -= history.getTot_protein();
      supposedFat -= history.getTot_fat();
    }
    return SupposedNutrition.of(supposedKcal, supposedCarbohydrate, supposedProtein, supposedFat);
  }

  public SupposedNutrition calculateMealTimeRate(String mealTime,
      SupposedNutrition supposedNutrition) {
    int kcal = (int)(supposedNutrition.kcal() * mealTimeRatios.get(mealTime));
    int carbohydrate = (int)(supposedNutrition.carbohydrate() * mealTimeRatios.get(mealTime));
    int protein = (int)(supposedNutrition.protein() * mealTimeRatios.get(mealTime));
    int fat = (int)(supposedNutrition.fat()* mealTimeRatios.get(mealTime));
    return SupposedNutrition.of(kcal,carbohydrate,protein,fat);
  }
}
