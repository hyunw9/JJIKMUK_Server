package com.mju.capstone.member.controller;

import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.dto.request.MemberUpdateRequest;
import com.mju.capstone.member.dto.response.MemberUpdateResponse;
import com.mju.capstone.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Member", description = "멤버(유저) 정보 조회/수정 관련")
@RequiredArgsConstructor

public class MemberController {

  private final MemberService memberService;

  @GetMapping("/member")
  @Operation(summary = "멤버 정보 조회", description = "멤버 정보 조회 API")
  public ResponseEntity<?> getMemberInfo() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ControllerMessage.of(
            SuccessMessage.OK,
            memberService.findByEmail(SecurityUtil.getLoginUserEmail()))
        );
  }

  @PatchMapping("/member/info")
  @Operation(summary = "멤버 정보 수정", description = "사용자 사용자 정보 수정 API")
  public ResponseEntity<?> updateMemberInfo(@RequestBody MemberUpdateRequest request) {
    MemberUpdateResponse response = memberService.updateMemberInfo(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }
}
