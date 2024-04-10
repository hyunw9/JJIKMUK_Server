package com.mju.capstone.Controller;

import com.mju.capstone.dto.SignUpResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @PostMapping("/")
  public ResponseEntity<SignUpResponse> returnTest(){
    SignUpResponse response = SignUpResponse.builder()
        .isSuccess(true)
        .message("성공")
        .build();

    return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
  }

}
