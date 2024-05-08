package com.mju.capstone.food.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HistoryEvent extends ApplicationEvent {

  private final String email;
  private final int kcal;
  private final int carbohydrate;
  private final int protein;
  private final int fat;

  public HistoryEvent(Object source, String email, int kcal, int carbohydrate, int protein,
      int fat) {
    super(source);
    this.email = email;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
  }
}
