package com.mju.capstone.recommend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 메뉴 추천")
public record MenuRecommendRequest(
    @Schema(name = "아침 / 점심")
    String mealTime,
    @Schema(name = "담백한 / 매운 (등등)")
    String tasteType,
    @Schema(name = "한식 / 중식 / 일식 / 양식")
    String menuCountry,
    @Schema(name = "요리 / 배달")
    String cookOrDelivery
) {

}
