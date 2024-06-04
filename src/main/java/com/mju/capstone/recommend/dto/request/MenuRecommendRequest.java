package com.mju.capstone.recommend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 메뉴 추천")
public record MenuRecommendRequest(
    @Schema(name = "아침 / 점심 / 저녁")
    String mealTime,
    @Schema(name = "담백한 / 매운 (등등)")
    String tasteType,
    @Schema(name = "한식 / 중식 / 일식 / 양식")
    String menuCountry,
    @Schema(name = "요리 / 배달")
    String cookOrDelivery,
    @Schema(name = "재료, 고기/야채 등 ")
    String ingredient
) {

}
