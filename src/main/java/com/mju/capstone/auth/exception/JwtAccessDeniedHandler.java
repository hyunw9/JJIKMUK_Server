package com.mju.capstone.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.global.response.ControllerMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private static ControllerMessage exceptionMessage ;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    exceptionMessage = ControllerMessage.builder()
        .status(HttpStatus.FORBIDDEN)
        .message("잘못된 접근입니다.")
        .build();
    try(OutputStream os = response.getOutputStream()){
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(os,exceptionMessage);
      os.flush();
    }
  }
}
