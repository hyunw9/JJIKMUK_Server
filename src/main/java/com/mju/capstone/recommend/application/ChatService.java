package com.mju.capstone.recommend.application;

import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import com.mju.capstone.member.service.GoalService;
import com.mju.capstone.member.service.MemberService;
import com.mju.capstone.recommend.domain.GptManager;
import com.mju.capstone.recommend.dto.request.MenuRecommendRequest;
import com.mju.capstone.recommend.dto.response.SupposedNutrition;
import com.mju.capstone.recommend.dto.response.TotalRecommendResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

  private final GptManager gptManager;
  private final StaticFoodService staticFoodService;
  private final MemberService memberService;
  private final GoalService goalService;

  public List<TotalRecommendResponse> getChatResponse(MenuRecommendRequest menuRecommendRequest) {

    GoalNutritionResponse memberGoal = goalService.getMemberGoal();
    SupposedNutrition supposedNutrition = goalService.calculateSupposedNutrition(memberGoal);
    SupposedNutrition calculatedSupposedNutrition = goalService.calculateMealTimeRate(
        menuRecommendRequest.mealTime(), supposedNutrition);

    String prompt = createNutritionPrompt(menuRecommendRequest, calculatedSupposedNutrition);
    log.info(prompt);

    List<String> response = gptManager.sendOpenAIRequest(prompt);

//    List<Menu> recommends = response.menus();

    return response.stream().map(this::getTotalRecommendResponseByFoodName)
        .collect(Collectors.toUnmodifiableList());
  }

  private String createNutritionPrompt(MenuRecommendRequest request,
      SupposedNutrition supposedNutrition) {
    return String.format(
        "사용자가 %s에 %s로 먹을 주재료가 %s이고 영양소가 %d carbohydrate, %d protein, %d fat 을 만족할 수 있는 식단을 " +
            "업로드한 파일 내에서 추천해줘. 응답 형식은 다른 말 없이 무조건 다음과 같아야 해 : JSON  [String]",
        request.mealTime(), request.cookOrDelivery(), request.ingredient()
        , supposedNutrition.carbohydrate(), supposedNutrition.protein(), supposedNutrition.fat()
    );
  }

  public TotalRecommendResponse getTotalRecommendResponseByFoodName(String response) {
    Food food = staticFoodService.findFoodByName(response);

    return TotalRecommendResponse.of(food.getName(), food.getKcal(),
        food.getCarbohydrate(), food.getProtein(), food.getFat(), food.getImgUrl(),
        food.getAmount(), food.getServing());
  }
}
