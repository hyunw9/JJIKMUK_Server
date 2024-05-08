package com.mju.capstone.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 업로드 요청")
public record ItemCreateRequest(

    @Schema(description = "음식 이름", example = "불고기")
    String name,
    @Schema(description = "양 | int ",example = "230",type = "Integer")
    int amount,
    @Schema(description = "인분",example = "3", type = "Integer")
    int serving,
    int kcal,
    int carbohydrate,
    int protein,
    int fat,
    @Schema(description = "파일 이름",example = "/bulgogi.jpg", type = "String")
    String fileName
    ) {
}
