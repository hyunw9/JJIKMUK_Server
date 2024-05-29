package com.mju.capstone.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 섭취 히스토리")
public record HistoryResponse(

    @Schema(description = "History PK")
    Long historyId,
    int tot_kcal,
    int tot_carbohydrate,
    int tot_protein,
    int tot_fat
) {

  public static HistoryResponse of(Long historyId,int tot_kcal, int tot_carbohydrate,
      int tot_protein, int tot_fat) {
    return new HistoryResponse(historyId, tot_kcal, tot_carbohydrate, tot_protein, tot_fat);
  }

  public static HistoryResponse defaultInstance(Long historyId){
    return new HistoryResponse(historyId,0,0,0,0);
  }
}
