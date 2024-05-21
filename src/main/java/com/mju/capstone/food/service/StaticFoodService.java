package com.mju.capstone.food.service;

import com.mju.capstone.food.dto.response.FoodResponse;
import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.repository.StaticFoodRepository;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.message.ErrorMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaticFoodService {

  private final StaticFoodRepository foodRepository;

  public List<String> searchFoodNameByString(String name) {

    return foodRepository.findFoodNameByNameContaining(name).stream()
        .map(Food::getName)
        .collect(Collectors.toUnmodifiableList());
  }

  public FoodResponse findFoodInfo(String name) {

    Food food = findFoodByName(name);
    return FoodResponse.of(food.getName(), food.getKcal(), food.getCarbohydrate(),
        food.getProtein(),
        food.getFat(), food.getImgUrl());
  }

  public Food findFoodByName(String name) {
    return foodRepository.findByName(name).orElseThrow(
        () -> new NotFoundException(ErrorMessage.FOOD_NOT_FOUND)
    );
  }

}
