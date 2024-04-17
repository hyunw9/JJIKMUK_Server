package com.mju.capstone.auth.dto.util;

import com.mju.capstone.auth.dto.response.MemberRes;
import com.mju.capstone.auth.repository.entity.Member;

public class AuthUtil {

  public static MemberRes of(Member member){

    return MemberRes.builder()
        .id(member.getId())
        .email(member.getEmail())
        .role(member.getRole())
        .build();
  }

}
