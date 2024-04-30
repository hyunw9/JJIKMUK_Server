package com.mju.capstone.auth.service;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.auth.dto.response.LoginRes;
import com.mju.capstone.auth.dto.response.MemberRes;
import com.mju.capstone.auth.dto.util.AuthUtil;
import com.mju.capstone.auth.event.RegistrationCompleteEvent;
import com.mju.capstone.auth.exception.AlreadyRegisteredException;
import com.mju.capstone.auth.repository.entity.Role;
import com.mju.capstone.global.security.provider.TokenProvider;
import com.mju.capstone.global.security.token.dto.TokenReq;
import com.mju.capstone.global.security.token.dto.TokenRes;
import com.mju.capstone.global.security.token.entity.RefreshToken;
import com.mju.capstone.global.security.token.repository.RefreshTokenRepository;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.MemberRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public MemberRes signup(MemberReq memberReq){

    if(memberRepository.existsByEmail(memberReq.email())){
      throw new AlreadyRegisteredException();
    }

    String password = passwordEncoder.encode(memberReq.password());

    Member member = Member.builder()
        .email(memberReq.email())
        .password(password)
        .role(Role.valueOf("USER"))
        .weight(memberReq.weight())
        .height(memberReq.height())
        .birth(memberReq.birth())
        .dietPlan(memberReq.birth())
        .nickname(memberReq.nickname())
        .build();

    memberRepository.save(member);

    publisher.publishEvent(new RegistrationCompleteEvent(this,memberReq));

    return AuthUtil.of(member);

  }

  @Transactional
  public LoginRes login(LoginReq loginReq){
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginReq.email(),loginReq.password());

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    TokenRes tokenRes = tokenProvider.generateToken(authentication);

    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenRes.refreshToken())
        .build();

    refreshTokenRepository.save(refreshToken);

    return LoginRes.builder()
        .accessToken(tokenRes.accessToken())
        .refreshToken(tokenRes.refreshToken())
        .accessTokenExpiresIn(tokenRes.accessTokenExpiresIn())
        .grantType(tokenRes.grantType())
        .build();
  }

  @Transactional
  public TokenRes reissue(TokenReq tokenReq){

    //1. refresh token 검증
    if( !tokenProvider.validateToken(tokenReq.refreshToken())){
      throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
    }

    //2. Access Token에서 인증 정보 가져오기
    Authentication authentication = tokenProvider.getAuthentication(tokenReq.accessToken());

    //3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져오기
    RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
        .orElseThrow(()-> new RuntimeException("로그아웃 된 사용자 입니다."));

    //4. Refresh Token 일치 검증
    if(!refreshToken.getValue().equals(tokenReq.refreshToken())){
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }

    //5. 새로운 토큰 생성
    TokenRes tokenRes = tokenProvider.generateToken(authentication);

    //6. 저장소 정보 업데이트
    RefreshToken newRefreshToken = refreshToken.updateValue(tokenReq.refreshToken());
    refreshTokenRepository.save(newRefreshToken);

    //토큰 발급
    return tokenRes;
  }

  public Long getId(Long id){
    Member member = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 아이디를 가진 유저가 없습니다."));
    return member.getId();
  }

}
