package com.mju.capstone.main.service;

import com.mju.capstone.food.dto.response.HistoryResponse;
import com.mju.capstone.food.service.HistoryService;
import com.mju.capstone.main.dto.MainResponse;
import com.mju.capstone.member.dto.response.GoalNutritionResponse;
import com.mju.capstone.member.service.GoalService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainFacadeService {

  private final GoalService goalService;
  private final HistoryService historyService;

  public MainResponse getMainData(){

    GoalNutritionResponse nutritionInfo = goalService.getMemberGoal();
    HashMap<Integer, HistoryResponse> historyInfoList = historyService.findRecentHistory();

    return MainResponse.of(nutritionInfo,historyInfoList);
  }
}
