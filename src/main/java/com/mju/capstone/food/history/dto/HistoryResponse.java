package com.mju.capstone.food.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 섭취 히스토리")
public record HistoryResponse(

    @Schema(description = "사용자 PK")
    Long memberId,

    @Schema(description = "현재로부터 차이나는 일 수를 표시")
    long dateDiff,
    int tot_kcal,
    int tot_carbohydrate,
    int tot_protein,
    int tot_fat
) {

  public static HistoryResponse of(Long memberId, long dateDiff,int tot_kcal, int tot_carbohydrate,
      int tot_protein, int tot_fat) {
    return new HistoryResponse(memberId,dateDiff, tot_kcal, tot_carbohydrate, tot_protein, tot_fat);
  }
}
