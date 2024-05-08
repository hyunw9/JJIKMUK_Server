package com.mju.capstone.global.response.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"ID에 해당하는 멤버가 존재하지 않습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"내부 서버 오류"),
  ALREADY_REGISTERED_ERROR(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 유저입니다."),
  REFRESH_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED.value(), "Refresh Token이 유효하지 않습니다."),
  USER_ALREADY_LOGOUT(HttpStatus.BAD_REQUEST.value(), "이미 로그아웃 된 사용자 입니다."),
  UNVALID_TOKEN_EXCEPTIION(HttpStatus.FORBIDDEN.value(), "토큰의 유저 정보가 일치하지 않습니다."),
  UNAUTHORIZED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "비정상적인 토큰입니다."),
  FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN.value(), "비정상적인 접근입니다."),
  ARGUMENT_EXCEED_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "인자가 지정된 값을 초과합니다."),
  ;
  private final int status;
  private final String message;
}
