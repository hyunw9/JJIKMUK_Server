package com.mju.capstone.auth.repository.entity;

public enum Gender {

  MALE("MALE"),
  FEMALE("FEMALE");

  private final String value;

  Gender(String value) {
    this.value = value;
  }
}
