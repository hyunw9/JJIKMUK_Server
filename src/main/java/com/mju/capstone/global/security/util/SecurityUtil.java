package com.mju.capstone.global.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

  private SecurityUtil(){
  }

  public static Long getCurrentMemberId(){
    //SecurityContext의 Authentication 을 이용해 회원 정보 가져오기

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication == null || authentication.getName()==null ){
      throw new RuntimeException("Security Context에 인증 정보가 없습니다. ");
    }

    return Long.parseLong(authentication.getName());
  }

  public static String getLoginUserEmail(){

    UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user.getUsername();
  }

}
