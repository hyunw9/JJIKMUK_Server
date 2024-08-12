package com.mju.capstone.member.entity;

import com.mju.capstone.auth.repository.entity.Role;
import com.mju.capstone.member.dto.request.MemberUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private String nickname;

  private int height;

  private int weight;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private int birth;

  private int level;

  private int dietPlan;

  @Builder
  public Member(String email, String password, Role role, String nickname, int height, int weight,
      Gender gender, int birth, int level, int dietPlan) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.nickname = nickname;
    this.height = height;
    this.weight = weight;
    this.gender = gender;
    this.birth = birth;
    this.level = level;
    this.dietPlan = dietPlan;
  }

  public boolean updateMemberInfo(MemberUpdateRequest updateRequest) {
    boolean requiresRecalculation = false;

    updateRequest.nickname().ifPresent(this::updateNickname);

//    if (updateRequest.password().isPresent()) {
//      updatePassword(updateRequest.password().get());
//    }

    if (updateRequest.height().isPresent() && updateRequest.height().get() != this.height) {
      this.height = updateRequest.height().get();
      requiresRecalculation = true;
    }
    if (updateRequest.weight().isPresent() && updateRequest.weight().get() != this.weight) {
      this.weight = updateRequest.weight().get();
      requiresRecalculation = true;
    }
    if (updateRequest.gender().isPresent() && updateRequest.gender().get() != this.gender) {
      this.gender = updateRequest.gender().get();
      requiresRecalculation = true;
    }
    if (updateRequest.birth().isPresent() && updateRequest.birth().get() != this.birth) {
      this.birth = updateRequest.birth().get();
      requiresRecalculation = true;
    }
    if (updateRequest.level().isPresent() && updateRequest.level().get() != this.level) {
      this.level = updateRequest.level().get();
      requiresRecalculation = true;
    }
    if (updateRequest.dietPlan().isPresent() && updateRequest.dietPlan().get() != this.dietPlan) {
      this.dietPlan = updateRequest.dietPlan().get();
      requiresRecalculation = true;
    }
    return requiresRecalculation;
  }

//  private void updatePassword(String password) {
//    if (password != null && !password.isEmpty()) {
//      this.password = password;
//    }
//  }

  public void updatePassword(String encodedPassword) {
    if (encodedPassword != null && !encodedPassword.isEmpty()) {
      this.password = encodedPassword;
    }
  }

  private void updateNickname(String nickname) {
    if (nickname != null && !nickname.isEmpty()) {
      this.nickname = nickname;
    }
  }

}
