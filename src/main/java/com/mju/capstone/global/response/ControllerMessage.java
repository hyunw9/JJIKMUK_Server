package com.mju.capstone.global.response;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ControllerMessage {

  private HttpStatus status;
  private String message;
  private Object data;

  @Builder
  public ControllerMessage(HttpStatus status, String message, Object data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public ControllerMessage(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  @Builder
  public ControllerMessage(String message, Object data) {
    this.message = message;
    this.data = data;
  }
}
