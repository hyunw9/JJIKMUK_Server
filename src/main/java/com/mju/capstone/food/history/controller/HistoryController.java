package com.mju.capstone.food.history.controller;

import com.mju.capstone.food.history.service.HistoryService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping("/history")
  public ResponseEntity<ControllerMessage<?>> getHistory(){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,historyService.findRecentHistory()));
  }
}
