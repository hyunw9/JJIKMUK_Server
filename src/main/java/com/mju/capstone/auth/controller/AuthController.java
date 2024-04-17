package com.mju.capstone.auth.controller;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.auth.service.AuthService;
import com.mju.capstone.global.security.token.dto.TokenReq;
import com.mju.capstone.global.response.ControllerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberReq memberReq){
    return new ResponseEntity<>(new ControllerMessage(HttpStatus.CREATED,"성공", authService.signup(memberReq)),HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq loginReq){
    return new ResponseEntity<>(new ControllerMessage(HttpStatus.OK,"성공",authService.login(loginReq)),HttpStatus.OK);
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(@RequestBody TokenReq tokenReq){
    return new ResponseEntity<>(new ControllerMessage(HttpStatus.OK,"성공",authService.reissue(
        tokenReq)),HttpStatus.OK);
  }
}
