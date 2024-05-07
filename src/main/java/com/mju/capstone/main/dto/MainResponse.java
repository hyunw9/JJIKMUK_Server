package com.mju.capstone.main.dto;

import com.mju.capstone.food.history.dto.HistoryResponse;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;

@Schema(description = "메인 화면 응답")
public record MainResponse(

    @Schema(description = "사용자가 설정한 목표 영양소")
    GoalNutritionResponse goalNutritionInfo,
    @Schema(description = "현재로부터 -7일 까지 히스토리")
    HashMap<Long, HistoryResponse> historyInfoList
) {

  public static MainResponse of(GoalNutritionResponse goalNutritionInfo, HashMap<Long, HistoryResponse> historyInfoList){
    return new MainResponse(goalNutritionInfo, historyInfoList);
  }
}
