package com.mju.capstone.recommend.application;

import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import com.mju.capstone.member.service.GoalService;
import com.mju.capstone.recommend.domain.GptManager;
import com.mju.capstone.recommend.dto.request.MenuRecommendRequest;
import com.mju.capstone.recommend.dto.response.Menu;
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
  private final GoalService goalService;

  public List<TotalRecommendResponse> getChatResponse(MenuRecommendRequest menuRecommendRequest) {

    GoalNutritionResponse memberGoal = goalService.getMemberGoal();
    SupposedNutrition supposedNutrition = goalService.calculateSupposedNutrition(memberGoal);
    SupposedNutrition calculatedSupposedNutrition = goalService.calculateMealTimeRate(
        menuRecommendRequest.mealTime(), supposedNutrition);

    String prompt = createNutritionPrompt(menuRecommendRequest, calculatedSupposedNutrition);
    log.info(prompt);

    List<Menu> response = gptManager.sendOpenAIRequest(prompt);

//    List<Menu> recommends = response.menus();

    return response.stream().map(this::getTotalRecommendResponseByFoodName)
        .collect(Collectors.toUnmodifiableList());
  }

  private String createNutritionPrompt(MenuRecommendRequest request,
      SupposedNutrition supposedNutrition) {
    String prefPrompt = "";
    if(request.tasteType().equals("") && request.ingredient().equals("")){
      prefPrompt = "사용자는 모든 음식을 선호해.";
    }
    else{
      prefPrompt = "사용자는 " + request.tasteType() + " " + request.ingredient() + " 음식을 선호해.";
    }
    return String.format(
        "사용자가 %s %s 먹을 식단을 추천해줘. %d carbohydrate, %d protein, %d fat 을 섭취해야 해." + prefPrompt
            + " 응답 형식은 다른 말 없이 무조건 다음과 같아야 해 : JSON [String , int]",
        request.mealTime(),request.cookOrDelivery(),supposedNutrition.carbohydrate(),
        supposedNutrition.protein(),supposedNutrition.fat(),request.tasteType(),request.ingredient()
    );
  }

  public TotalRecommendResponse getTotalRecommendResponseByFoodName(Menu response) {
    Food food = staticFoodService.findFoodByName(response.name());
    int amount = response.amount();
    int kcal = (int) ((double) food.getKcal() * amount / 100);
    int carbo = (int) ((double) food.getCarbohydrate() * amount / 100);
    int protein = (int) ((double) food.getProtein() * amount / 100);
    int fat = (int) ((double) food.getFat() * amount / 100);

    return TotalRecommendResponse.of(food.getName(), kcal,
        carbo, protein, fat, food.getImgUrl(),
        amount, food.getServing());
  }
}
