package com.mju.capstone.auth.event;

import com.mju.capstone.auth.dto.request.MemberReq;
import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {

  private final MemberReq memberReq;

  public RegistrationCompleteEvent(Object source, MemberReq memberReq) {
    super(source);
    this.memberReq = memberReq;
  }

  public MemberReq getMemberReq() {
    return memberReq;
  }
}
