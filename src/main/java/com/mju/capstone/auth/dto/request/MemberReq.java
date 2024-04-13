package com.mju.capstone.auth.dto.request;

import com.mju.capstone.user.entity.Gender;
import lombok.Builder;

public record MemberReq(

    String email,
    String password,
    String nickname,
    int height,
    int weight,
    Gender gender,
    int birth,
    int level,
    int dietPlan
) {

  @Builder
  public MemberReq(String email, String password, String nickname, int height, int weight,
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
