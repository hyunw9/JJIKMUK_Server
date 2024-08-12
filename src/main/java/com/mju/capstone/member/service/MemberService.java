package com.mju.capstone.member.service;

import com.mju.capstone.auth.event.CalculationEvent;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.dto.request.MemberUpdateRequest;
import com.mju.capstone.member.dto.request.NutritionCalculatorRequest;
import com.mju.capstone.member.dto.response.MemberInfoResponse;
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

  public MemberInfoResponse findMemberInfoByEmail(String email) {
    Member member = findByEmail(email);
    return MemberInfoResponse.from(member.getId(), member.getEmail(), member.getRole(), member.getNickname(), member.getBirth(), member.getHeight(), member.getWeight(), member.getLevel(), member.getDietPlan());
  }

  @Transactional
  public MemberUpdateResponse updateMemberInfo(MemberUpdateRequest updateRequest) {
    MemberInfoResponse memberInfo = findMemberInfoByEmail(SecurityUtil.getLoginUserEmail());

    Member member = findByEmail(memberInfo.email());

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
