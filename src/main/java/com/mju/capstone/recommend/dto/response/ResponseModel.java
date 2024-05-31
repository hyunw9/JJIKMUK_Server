package com.mju.capstone.recommend.dto.response;

public record ResponseModel(
    String name
) {

  public ResponseModel(String name) {
    this.name=name;
  }
}
