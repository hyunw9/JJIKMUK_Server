package com.mju.capstone.member.entity;

import com.mju.capstone.auth.repository.entity.Role;
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

}
