package com.mju.capstone.recommend.presentation;

import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.recommend.application.ChatService;
import com.mju.capstone.recommend.dto.request.MenuRecommendRequest;
import com.mju.capstone.recommend.dto.response.TotalRecommendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "메뉴 추천", description = "OpenAPI 를 이용한 메뉴 추천")
public class RecommendController {

  private final ChatService chatService;

  @PostMapping("/chat/recommendation")
  @Operation(
      summary = "메뉴 추천",
      description = "OPENAPI 를 이용하여 학습된 모델로부터 메뉴를 추천받고, 이미지와 함께 반환합니다. "
  )
  @ApiResponse(
      responseCode = "202",
      description = "추천 성공",
      content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = TotalRecommendResponse.class))))
  public ResponseEntity<?> getMenuRecommendation(
      @RequestBody MenuRecommendRequest menuRecommendRequest
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ControllerMessage.of(SuccessMessage.CREATED_SUCCESS,
            chatService.getChatResponse(menuRecommendRequest)));
  }

}
