package com.mju.capstone.recommend.domain;

import com.mju.capstone.food.entity.Food;

public interface FoodRepository {

  Food findByName(String name);
}
