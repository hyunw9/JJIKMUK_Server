package com.mju.capstone.recommend.dto.response;

public record Menu(
    String name
) {

  public Menu(String name) {
    this.name = name;
  }
}
