package com.mju.capstone.food.history.entity;

import com.mju.capstone.global.baseEntity.BaseTimeEntity;
import com.mju.capstone.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class History extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private int tot_kcal;

  private int tot_carbohydrate;

  private int tot_protein;

  private int tot_fat;

  public History(Member member, int tot_kcal, int tot_carbohydrate, int tot_protein, int tot_fat) {
    this.member = member;
    this.tot_kcal = tot_kcal;
    this.tot_carbohydrate = tot_carbohydrate;
    this.tot_protein = tot_protein;
    this.tot_fat = tot_fat;
  }
}
