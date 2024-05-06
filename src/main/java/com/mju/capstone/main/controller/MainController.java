package com.mju.capstone.main.controller;

import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.main.service.MainFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Main", description = "메인화면 관련")
public class MainController {

  private final MainFacadeService mainFacadeService;

  @GetMapping("/main")
  @Operation(
      summary = "메인화면 요청 API",
      description = "유저 목표 영양소와 -7일 까지의 히스토리를 반환합니다."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "메인 화면 조회 성공"),
      @ApiResponse(responseCode = "400", description = "메인 화면 조회 실패")
  })
  public ResponseEntity<?> getMainData(){
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(SuccessMessage.OK,mainFacadeService.getMainData()));
  }
}
