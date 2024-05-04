package com.mju.capstone.preference.entity;

import com.mju.capstone.global.baseEntity.BaseTimeEntity;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.menu.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Preference extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Builder
  public Preference(Member member, Menu menu) {
    this.member = member;
    this.menu = menu;
  }
}
