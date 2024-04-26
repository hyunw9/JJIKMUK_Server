package com.mju.capstone.auth.service;

import com.mju.capstone.auth.event.RegistrationCompleteEvent;
import com.mju.capstone.auth.repository.MemberRepository;
import com.mju.capstone.auth.repository.entity.Member;
import com.mju.capstone.auth.service.calculator.CalorieCalculator;
import com.mju.capstone.auth.service.calculator.InitialCalorieCalculatorFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;


  @EventListener
  public void initializeMemberInfo(RegistrationCompleteEvent event){
    //칼로리 계산
    CalorieCalculator calorieCalculator = InitialCalorieCalculatorFactory.getCalorieCalculator(event.getMemberReq());
    String email = event.getMemberReq().email();
    Member member = memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("이메일에 해당하는 유저가 없습니다."));
    log.info(event.getMemberReq().email());
    log.info(calorieCalculator.calculateInitialUserCalorie()+"");
    member.initializeCalorie(calorieCalculator.calculateInitialUserCalorie());

    memberRepository.save(member);
  }

}
