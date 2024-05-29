package com.mju.capstone.recommend.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.recommend.config.AppConfig;
import com.mju.capstone.recommend.domain.GptManager;
import com.mju.capstone.recommend.dto.request.MenuRecommendRequest;
import com.mju.capstone.recommend.dto.response.RecommendResponse;
import com.mju.capstone.recommend.dto.response.TotalRecommendResponse;
import com.mju.capstone.recommend.repository.FoodRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

  private final GptManager gptManager;
  private final AppConfig appConfig;
  private final ObjectMapper objectMapper;
  private final StaticFoodService staticFoodService;
  private final FoodRepositoryImpl foodRepository;

  public TotalRecommendResponse getChatResponse(MenuRecommendRequest menuRecommendRequest) {
    StringBuilder sb = new StringBuilder();
    sb.append("업로드한 파일 내에서 ").append(menuRecommendRequest.cookOrDelivery()).append("로 ");
    sb.append(menuRecommendRequest.mealTime()).append("에 먹을 ")
        .append(menuRecommendRequest.tasteType()).append("한 ").append(menuRecommendRequest.menuCountry())
        .append("을 추천해줘.\n")
        .append("답은 1개 여야하고, 다양하게 추천해줘. 응답 형식: JSON  { name: String }");

    RecommendResponse response = gptManager.sendOpenAIRequest(sb.toString());
    log.info(sb.toString());
    return getTotalRecommendResponseByFoodName(response);
  }

  public TotalRecommendResponse getTotalRecommendResponseByFoodName(RecommendResponse response){
    Food food = foodRepository.findByName(response.name());

    return TotalRecommendResponse.of(food.getName(),food.getKcal(),
        food.getCarbohydrate(), food.getProtein(), food.getFat(), food.getImgUrl(),food.getAmount(),food.getServing());
  }
}
