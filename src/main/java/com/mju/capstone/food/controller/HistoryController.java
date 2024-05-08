package com.mju.capstone.food.controller;

import com.mju.capstone.food.service.HistoryService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Food", description = "사용자 음식 섭취 기록 관리 관련")
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping("/history")
  @Operation(
      summary = "히스토리 반환 요청",
      description = "유저 목표 영양소와 -7일 까지의 히스토리를 반환합니다."
  )
  public ResponseEntity<ControllerMessage<?>> getHistory(){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,historyService.findRecentHistory()));
  }
}
