package com.mju.capstone.food.controller;

import com.mju.capstone.food.dto.request.ItemCreateRequest;
import com.mju.capstone.food.dto.response.ItemResponse;
import com.mju.capstone.food.service.ItemService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Food")
public class ItemController {

  private final ItemService itemService;

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

  @GetMapping("/items")
  @Operation(
      summary = "음식 메뉴 검색",
      description = "음식 메뉴 데이터베이스에서 존재하는 음식을 검색합니다. "
  )
  @ApiResponse(
      responseCode = "200",
      description = "조회 성공",
      content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class))))

  public ResponseEntity<?> searchItemByItemName(
      @Parameter(required = true, example = "제육")
      @RequestParam String itemName
  ){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,itemService.searchItemsByString(itemName)));
  }
}
