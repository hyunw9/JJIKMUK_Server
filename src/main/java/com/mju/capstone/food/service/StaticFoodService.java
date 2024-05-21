package com.mju.capstone.food.service;

import com.mju.capstone.food.dto.response.FoodResponse;
import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.repository.StaticFoodRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaticFoodService {

  private final StaticFoodRepository foodRepository;

  public List<String> searchFoodNameByString(String foodName) {

    return foodRepository.findFoodNameByNameLike(foodName).stream().map(food -> food.getName())
        .collect(Collectors.toUnmodifiableList());
  }

  public FoodResponse findFoodInfo(String foodName){

    Food food = foodRepository.findFoodByName(foodName);
    return FoodResponse.of(food.getName(),food.getKcal(),food.getCarbohydrate(),food.getProtein(),
        food.getFat(), food.getImgUrl());
  }

}
