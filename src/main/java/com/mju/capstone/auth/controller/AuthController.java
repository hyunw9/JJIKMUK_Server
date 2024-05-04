package com.mju.capstone.auth.controller;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.auth.service.AuthService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.global.security.token.dto.TokenReq;
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
  public ResponseEntity<?> signup(@RequestBody MemberReq memberReq) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ControllerMessage.of(SuccessMessage.CREATED_SUCCESS, (authService.signup(memberReq))));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK, authService.login(loginReq)));
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(@RequestBody TokenReq tokenReq) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK, authService.reissue(tokenReq)));
  }
}
