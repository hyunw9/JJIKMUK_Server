package com.mju.capstone.food.controller;

import com.mju.capstone.food.dto.request.ItemCreateRequest;
import com.mju.capstone.food.service.ItemService;
import com.mju.capstone.food.service.StaticFoodService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Item", description = "사용자가 입력한 음식")
public class ItemController {

  private final ItemService itemService;
  private final StaticFoodService foodService;

  @PostMapping("/item")
  @Operation(
      summary = "유저가 새로 섭취한 음식 등록",
      description = "유저가 섭취한 음식을 등록하고, 히스토리가 존재하지 않다면 생성 후 갱신, 존재한다면 갱신합니다."
  )
  public ResponseEntity<?> postItem(@RequestBody ItemCreateRequest itemCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ControllerMessage.of(SuccessMessage.CREATED_SUCCESS,
            itemService.uploadItem(itemCreateRequest)));
  }

  @GetMapping("/item/date")
  @Operation(
      summary = "유저가 날짜별로 섭취한 음식 조회",
      description = "LocalDate 형식으로 유저가 해당 일에 어떤 음식을 섭취했는지 조회합니다. "
  )
  public ResponseEntity<?> getItemByLocalDate(
      @Parameter(required = true, example = "2024-05-08")
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK, itemService.findItemHistoryByDate(date)));
  }

}
