package com.mju.capstone.member.service;

import com.mju.capstone.auth.event.CalculationEvent;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.dto.request.MemberUpdateRequest;
import com.mju.capstone.member.dto.request.NutritionCalculatorRequest;
import com.mju.capstone.member.dto.response.MemberUpdateResponse;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final ApplicationEventPublisher publisher;

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND));
  }

  @Transactional
  public MemberUpdateResponse updateMemberInfo(MemberUpdateRequest updateRequest) {
    Member member = findByEmail(SecurityUtil.getLoginUserEmail());

    if (updateRequest.password().isPresent()) {
      String hashedPassword = passwordEncoder.encode(updateRequest.password().get());
      updateRequest = new MemberUpdateRequest(
          Optional.of(hashedPassword),
          updateRequest.nickname(),
          updateRequest.height(),
          updateRequest.weight(),
          updateRequest.gender(),
          updateRequest.birth(),
          updateRequest.level(),
          updateRequest.dietPlan()
      );
    }

    boolean requiresRecalculation = member.updateMemberInfo(updateRequest);
    memberRepository.save(member);

    if (requiresRecalculation) {
      publishRecalculationEvent(member);
    }
    return MemberUpdateResponse.fromMember(member);
  }

  private void publishRecalculationEvent(Member member) {
    NutritionCalculatorRequest calculatorRequest = NutritionCalculatorRequest.fromMember(member);
    publisher.publishEvent(new CalculationEvent(this, calculatorRequest));
  }
}
