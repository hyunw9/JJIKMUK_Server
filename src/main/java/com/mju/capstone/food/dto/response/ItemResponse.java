package com.mju.capstone.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 추가 후 응답")
public record ItemResponse(

    @Schema(description = "음식 PK",example = "1", type = "Long")
    Long id,
    String name,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    String fileName
) {

  public static ItemResponse of(Long id, String name, int kcal, int carbohydrate, int protein,
      int fat, String fileName) {
    return new ItemResponse(id, name, kcal, carbohydrate, protein, fat, fileName);
  }
}
