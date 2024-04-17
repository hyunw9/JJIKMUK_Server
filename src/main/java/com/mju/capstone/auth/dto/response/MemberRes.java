package com.mju.capstone.auth.dto.response;

import com.mju.capstone.auth.repository.entity.Role;
import lombok.Builder;

public record MemberRes(

    Long id,
    String email,
    Role role
) {

  @Builder
  public MemberRes(Long id, String email, Role role) {
    this.id = id;
    this.email = email;
    this.role = role;
  }
}
