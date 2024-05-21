package com.mju.capstone.food.service;

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

}
