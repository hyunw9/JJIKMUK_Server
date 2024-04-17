package com.mju.capstone.auth.controller;


import com.mju.capstone.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/query")
public class AuthQueryController {

  private final AuthService authService;

  @GetMapping("/")
  public ResponseEntity<?> getId(@RequestHeader Long memberId){
    return ResponseEntity.ok(authService.getId(memberId));
  }
}