package com.mju.capstone.member.dto.response;

import com.mju.capstone.member.entity.Member;

public record MemberUpdateResponse(

    Long memberId,
    String nickName,
    int birth,
    int height,
    int weight,
    int level,
    int dietLevel
) {
  public static MemberUpdateResponse fromMember(Member member) {
    return new MemberUpdateResponse(member.getId(),member.getNickname(),member.getBirth(),
        member.getHeight(), member.getWeight(), member.getLevel(), member.getDietPlan());
  }
}
