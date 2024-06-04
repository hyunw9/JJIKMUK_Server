package com.mju.capstone.recommend.dto.response;

public record Menu(
    String name,
    int amount
) {

  public Menu(String name, int amount) {
    this.name = name; this.amount = amount;
  }
}
