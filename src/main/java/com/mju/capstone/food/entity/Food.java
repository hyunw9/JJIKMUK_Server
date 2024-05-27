package com.mju.capstone.food.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu_final")
public class Food {

  @Id
  @Column(name = "food_id")
  private String food_id;

  @Column(name = "name")
  private String name;

  @Column(name = "main_menu")
  private String main_menu;

  @Column(name = "kcal")
  private int kcal;

  @Column(name = "carbohydrate")
  private int carbohydrate;

  @Column(name = "protein")
  private int protein;

  @Column(name = "fat")
  private int fat;

  @Column(name = "img_url")
  private String imgUrl;

  @Column(name = "amount")
  private int amount;

  @Column(name = "serving")
  private int serving;

  @Builder
  public Food(String name, String main_menu, int kcal, int carbohydrate, int protein, int fat,
      String imgUrl, int amount) {
    this.name = name;
    this.main_menu = main_menu;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
    this.imgUrl = imgUrl;
    this.amount = amount;
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
        && fat == food.fat && Objects.equals(food_id, food.food_id) && Objects.equals(name,
        food.name) && Objects.equals(main_menu, food.main_menu) && Objects.equals(
        imgUrl, food.imgUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(food_id, name, main_menu, kcal, carbohydrate, protein, fat, imgUrl);
  }
}
