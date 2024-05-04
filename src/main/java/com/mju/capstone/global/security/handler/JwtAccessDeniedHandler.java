package com.mju.capstone.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.global.response.ControllerMessage;
import com.mju.capstone.global.response.message.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    exceptionMessage = ControllerMessage.of(ErrorMessage.FORBIDDEN_EXCEPTION);

    try(OutputStream os = response.getOutputStream()){
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(os,exceptionMessage);
      os.flush();
    }
  }
}
