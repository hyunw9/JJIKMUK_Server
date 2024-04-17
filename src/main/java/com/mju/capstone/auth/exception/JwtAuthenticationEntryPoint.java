package com.mju.capstone.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.global.response.ControllerMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private static  ControllerMessage exceptionDto ;
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    exceptionDto = ControllerMessage.builder()
        .status(HttpStatus.UNAUTHORIZED)
        .message("비정상적인 토큰입니다.")
        .build();

    try(OutputStream os = response.getOutputStream()){
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(os,exceptionDto);
      os.flush();
    }
  }
}
