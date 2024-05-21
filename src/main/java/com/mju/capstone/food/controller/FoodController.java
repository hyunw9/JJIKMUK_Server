package com.mju.capstone.food.controller;

import com.mju.capstone.food.dto.response.FoodResponse;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Food" , description = "메뉴 DB에 존재하는 음식 관련 서비스")
public class FoodController {

  private final StaticFoodService foodService;

  @GetMapping("/foods/string")
  @Operation(
      summary = "음식 메뉴 검색",
      description = "음식 메뉴 데이터베이스에서 존재하는 음식을 검색해 문자열로 반환합니다.. "
  )
  @ApiResponse(
      responseCode = "200",
      description = "조회 성공",
      content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = String.class))))

  public ResponseEntity<?> searchItemByItemName(
      @Parameter(required = true, example = "제육")
      @RequestParam String name
  ){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,foodService.searchFoodNameByString(name)));
  }

  @GetMapping("/food")
  @Operation(
      summary = "음식 정보 검색",
      description = "음식 메뉴 데이터베이스에서 존재하는 음식의 정보를 검색합니다. "
  )
  @ApiResponse(
      responseCode = "200",
      description = "조회 성공",
          content = @Content(
              schema = @Schema(implementation = FoodResponse.class))
  )
  public ResponseEntity<?> getItemInfoByName(
      @Parameter(required = true, example = "들깨국")
      @RequestParam String name
  ){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,foodService.findFoodInfo(name)));
  }

}
