package com.mju.capstone.food.item.entity;

import com.mju.capstone.food.history.entity.History;
import com.mju.capstone.global.baseEntity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Item extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "history_id")
  private History history;

  private String name;

  private int kcal;

  private int carbohydrate;

  private int protein;

  private int fat;

  public Item(History history, String name, int kcal, int carbohydrate, int protein, int fat) {
    this.history = history;
    this.name = name;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
  }
}
