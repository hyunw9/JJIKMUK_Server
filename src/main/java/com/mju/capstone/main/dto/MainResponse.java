package com.mju.capstone.main.dto;

import com.mju.capstone.food.history.dto.HistoryResponse;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import java.util.List;

public record MainResponse(

    GoalNutritionResponse goalNutritionInfo,
    List<HistoryResponse> historyInfoList
) {

  public static MainResponse of(GoalNutritionResponse goalNutritionInfo, List<HistoryResponse> historyInfoList){
    return new MainResponse(goalNutritionInfo, historyInfoList);
  }
}
