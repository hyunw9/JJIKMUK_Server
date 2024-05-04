package com.mju.capstone.global.response.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

  CREATED_SUCCESS(HttpStatus.CREATED.value(),"생성이 완료되었습니다."),
  OK(HttpStatus.OK.value(),"정상 요청 완료"),;

  private final int status;
  private final String message;

}
