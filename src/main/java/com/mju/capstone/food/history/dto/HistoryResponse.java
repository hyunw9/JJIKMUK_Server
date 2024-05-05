package com.mju.capstone.food.history.dto;

public record HistoryResponse(

    Long memberId,
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
