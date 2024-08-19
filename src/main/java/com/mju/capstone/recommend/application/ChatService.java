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
    log.info("response size : " + response.size());

    return response.stream().map(this::getTotalRecommendResponseByFoodName)
        .collect(Collectors.toUnmodifiableList());
  }

  private String createNutritionPrompt(MenuRecommendRequest request,
      SupposedNutrition supposedNutrition) {
    String prefPrompt = "";
    String tasteType = request.tasteType().equals("상관없음") ? "" : request.tasteType();
    String menuCountry = request.menuCountry().equals("상관없음") ? "" : request.menuCountry();
    String ingredient = request.ingredient().equals("상관없음") ? "" : request.ingredient();
    if(tasteType.equals("") && menuCountry.equals("") && ingredient.equals("")){
      prefPrompt = "사용자는 모든 음식을 선호해.";
    }
    else{
      prefPrompt = "사용자는 " + tasteType + " " + menuCountry + " " + ingredient + " 음식을 선호해.";
    }
    String result = String.format(
            "사용자가 %s으로 %s로 먹어야해. 탄수화물 %dg, 단백질 %dg, 지방 %dg 을 섭취해야 해." + prefPrompt,
            request.mealTime(),request.cookOrDelivery(),supposedNutrition.carbohydrate(),
            supposedNutrition.protein(),supposedNutrition.fat()
    );
    log.info(result);
    return result;
  }

  public TotalRecommendResponse getTotalRecommendResponseByFoodName(Menu response) {
    log.info(response.name());
    Food food = staticFoodService.findFoodByName(response.name());
    int amount = response.amount();
    int kcal = (int) ((double) food.getKcal() * amount / 100);
    log.info(String.valueOf(food.getKcal()) + " " + String.valueOf(kcal));
    int carbo = (int) ((double) food.getCarbohydrate() * amount / 100);
    int protein = (int) ((double) food.getProtein() * amount / 100);
    int fat = (int) ((double) food.getFat() * amount / 100);

    return TotalRecommendResponse.of(food.getName(), kcal,
        carbo, protein, fat, food.getImgUrl(),
        amount, food.getServing());
  }
}
