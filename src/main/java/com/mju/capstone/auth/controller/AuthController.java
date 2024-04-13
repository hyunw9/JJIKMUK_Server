package com.mju.capstone.auth.controller;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.auth.dto.response.MemberRes;
import com.mju.capstone.auth.service.AuthService;
import com.mju.capstone.auth.token.dto.TokenDto;
import com.mju.capstone.auth.token.dto.TokenRequest;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<MemberRes> signup(@RequestBody MemberReq memberReq){
    return ResponseEntity.ok(authService.signup(memberReq));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginReq loginReq){
    return ResponseEntity.ok(authService.login(loginReq));
  }

  @PostMapping("/reissue")
  public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequest tokenRequest){
    return ResponseEntity.ok(authService.reissue(tokenRequest));
  }
}
