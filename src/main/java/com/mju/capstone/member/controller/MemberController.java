package com.mju.capstone.member.controller;

import com.mju.capstone.member.service.GoalService;
import com.mju.capstone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final GoalService goalService;
  private final MemberService memberService;

  @GetMapping("/api/v1/main")
  public ResponseEntity<?> getMainUserNutrition(){

    return new ResponseEntity<>(goalService.getMemberGoal(), HttpStatus.OK);
  }
}
