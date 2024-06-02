package com.mju.capstone.member.entity;

import com.mju.capstone.member.dto.response.NutritionResponse;
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
public class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private int kcal;

  private int carbohydrate;

  private int protein;

  private int fat;

  @Builder
  public Goal(int fat, int protein, int carbohydrate, int kcal, Member member) {
    this.fat = fat;
    this.protein = protein;
    this.carbohydrate = carbohydrate;
    this.kcal = kcal;
    this.member = member;
  }

  public void updateGoal(NutritionResponse response){
    this.kcal = response.kcal();
    this.carbohydrate = response.carbohydrate();
    this.protein = response.protein();
    this.fat = response.fat();
  }
}
