package com.mju.capstone.member.service;

import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member findByEmail(String email){
    return memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("이메일에 해당하는 유저가 없습니다."));
  }
}
