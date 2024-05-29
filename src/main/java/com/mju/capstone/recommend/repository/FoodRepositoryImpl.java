package com.mju.capstone.recommend.repository;

import com.mju.capstone.food.entity.Food;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.recommend.domain.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepository {

  private final StaticFoodService staticFoodService;

  @Override
  public Food findByName(String name) {
    return staticFoodService.findFoodByName(name);
  }
}
