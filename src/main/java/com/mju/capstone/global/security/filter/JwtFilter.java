package com.mju.capstone.global.security.filter;

import com.mju.capstone.global.security.provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final String AUTHORIZATION_HEADER = "Authorization";
  private final String BEARER_PREFIX = "Bearer ";
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String jwt = resolveToken(request);

    //token 이 null 이 아닐 경우 context 에 authentication 을 넣는다.
    if(null != jwt && tokenProvider.validateToken(jwt)){
      Authentication authentication =tokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request,response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
      return bearerToken.split(" ")[1].trim();
    }
    return null;
  }


}
