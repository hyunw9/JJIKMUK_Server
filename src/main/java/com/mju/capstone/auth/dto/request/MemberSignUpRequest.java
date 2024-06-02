package com.mju.capstone.auth.dto.request;

import com.mju.capstone.member.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "회원 가입을 Request Body")
public record MemberSignUpRequest(

    @Schema(description = "사용자 이메일",example = "rkdgusdnr32@gmail.com")
    String email,

    @Schema(description = "비밀번호", example = "q1w2e3r4")
    String password,
    @Schema(description = "닉네임", example = "홍길동")
    String nickname,
    @Schema(description = "신장, int 형", example = "176")
    int height,
    @Schema(description = "몸무게, int 형", example = "65")
    int weight,
    @Schema(description = "성별, MALE | FEMALE", example = "MALE")
    Gender gender,
    @Schema(description = "생일, int 형 ", example = "2000")
    int birth,
    @Schema(description = "활동 지수, 1~5 int 형", example = "3")
    int level,
    @Schema(description = "다이어트 목표, 1~5 int 형", example = "5")
    int dietPlan
) {

  @Builder
  public MemberSignUpRequest(String email, String password, String nickname, int height, int weight,
      Gender gender, int birth, int level, int dietPlan) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.height = height;
    this.weight = weight;
    this.gender = gender;
    this.birth = birth;
    this.level = level;
    this.dietPlan = dietPlan;
  }
}
