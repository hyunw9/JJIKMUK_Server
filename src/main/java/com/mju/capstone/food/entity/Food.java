package com.mju.capstone.food.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Food {

  @Id
  private String id;

  private String name;

  private String main_menu;

  private int kcal;

  private int carbohydrate;

  private int protein;

  private int fat;

  private String imgUrl;

  @Builder
  public Food(String name, String main_menu, int kcal, int carbohydrate, int protein, int fat,
      String imgUrl) {
    this.name = name;
    this.main_menu = main_menu;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
    this.imgUrl = imgUrl;
  }

  public void insertImgUrl(String imgUrl){
    this.imgUrl = imgUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Food food = (Food) o;
    return kcal == food.kcal && carbohydrate == food.carbohydrate && protein == food.protein
        && fat == food.fat && Objects.equals(id, food.id) && Objects.equals(name,
        food.name) && Objects.equals(main_menu, food.main_menu) && Objects.equals(
        imgUrl, food.imgUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, main_menu, kcal, carbohydrate, protein, fat, imgUrl);
  }
}
