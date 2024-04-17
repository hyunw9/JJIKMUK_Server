package com.mju.capstone.global.security.service;

import com.mju.capstone.auth.repository.entity.Member;
import com.mju.capstone.auth.repository.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    return memberRepository.findByEmail(email)
        .map(this::createUserDetails)
        .orElseThrow(()->new UsernameNotFoundException("해당 유저는 존재하지 않습니다."));
  }

  private UserDetails createUserDetails(Member member){
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());
    return new User(
        String.valueOf(member.getId()),
        member.getPassword(),
        Collections.singletonList(grantedAuthority)
    );
  }
}
