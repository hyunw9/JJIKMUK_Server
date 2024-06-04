package com.mju.capstone.auth.controller;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberSignUpRequest;
import com.mju.capstone.auth.service.AuthService;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.global.security.token.dto.TokenReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name ="Auth", description = "인증 / 인가 관련")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "회원가입", description = "사용자 회원가입 API")
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberSignUpRequest memberSignUpRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ControllerMessage.of(SuccessMessage.CREATED_SUCCESS, (authService.signup(
                memberSignUpRequest))));
  }

  @Operation(summary = "로그인", description = "로그인 API")
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK, authService.login(loginReq)));
  }

  @Operation(summary = "토큰 갱신", description = "리프레시 토큰 갱신 API")
  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(@RequestBody TokenReq tokenReq) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK, authService.reissue(tokenReq)));
  }
}
