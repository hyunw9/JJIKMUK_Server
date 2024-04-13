package com.mju.capstone.auth.service;

import com.mju.capstone.auth.dto.request.LoginReq;
import com.mju.capstone.auth.dto.request.MemberReq;
import com.mju.capstone.auth.dto.response.MemberRes;
import com.mju.capstone.auth.exception.AlreadyRegisteredException;
import com.mju.capstone.auth.token.dto.TokenDto;
import com.mju.capstone.auth.entity.Member;
import com.mju.capstone.auth.entity.Role;
import com.mju.capstone.auth.repository.MemberRepository;
import com.mju.capstone.auth.token.dto.TokenRequest;
import com.mju.capstone.auth.dto.util.AuthUtil;
import com.mju.capstone.global.security.provider.TokenProvider;
import com.mju.capstone.auth.token.entity.RefreshToken;
import com.mju.capstone.auth.token.repository.RefreshTokenRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
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
        .build();

    memberRepository.save(member);
    return AuthUtil.of(member);

  }

  @Transactional
  public TokenDto login(LoginReq loginReq){
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginReq.email(),loginReq.password());

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    TokenDto tokenDto = tokenProvider.generateToken(authentication);

    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenDto.refreshToken())
        .build();

    refreshTokenRepository.save(refreshToken);

    return tokenDto;
  }

  @Transactional
  public TokenDto reissue(TokenRequest tokenRequest){

    //1. refresh token 검증
    if( !tokenProvider.validateToken(tokenRequest.refreshToken())){
      throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
    }

    //2. Access Token에서 인증 정보 가져오기
    Authentication authentication = tokenProvider.getAuthentication(tokenRequest.accessToken());

    //3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져오기
    RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
        .orElseThrow(()-> new RuntimeException("로그아웃 된 사용자 입니다."));

    //4. Refresh Token 일치 검증
    if(!refreshToken.getValue().equals(tokenRequest.refreshToken())){
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }

    //5. 새로운 토큰 생성
    TokenDto tokenDto = tokenProvider.generateToken(authentication);

    //6. 저장소 정보 업데이트
    RefreshToken newRefreshToken = refreshToken.updateValue(tokenRequest.refreshToken());
    refreshTokenRepository.save(newRefreshToken);

    //토큰 발급
    return tokenDto;
  }

  public Long getId(Long id){
    Member member = memberRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 아이디를 가진 유저가 없습니다."));
    return member.getId();
  }

}
