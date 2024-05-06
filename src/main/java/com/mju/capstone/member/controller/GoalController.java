package com.mju.capstone.member.controller;

import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.member.service.GoalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name ="Goal", description = "사용자 목표 영양소 관련")
public class GoalController {

  private final GoalService goalService;

  @GetMapping("/api/v1/goal")
  public ResponseEntity<?> getMainUserNutrition(){

    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,goalService.getMemberGoal()));
  }
}
