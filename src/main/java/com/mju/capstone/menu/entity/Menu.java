package com.mju.capstone.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String food_id;

  private String name;

  private int kcal;

  private int carbohydrate;

  private int protein;

  private int fat;

  @Builder
  public Menu(String name, int kcal, int carbohydrate, int protein, int fat) {
    this.name = name;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
  }
}
