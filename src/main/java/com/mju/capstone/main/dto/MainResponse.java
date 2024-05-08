package com.mju.capstone.main.dto;

import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;

@Schema(description = "메인 화면 응답")
public record MainResponse<K,V>(

    @Schema(description = "사용자가 설정한 목표 영양소")
    GoalNutritionResponse goalNutritionInfo,
    @Schema(description = "현재로부터 -7일 까지 히스토리")
    HashMap<K,V> historyInfoList
) {

  public static <K,V> MainResponse<K,V> of(GoalNutritionResponse goalNutritionInfo, HashMap<K,V> historyInfoList){
    return new MainResponse(goalNutritionInfo, historyInfoList);
  }
}
