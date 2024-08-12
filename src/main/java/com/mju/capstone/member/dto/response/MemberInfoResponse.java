package com.mju.capstone.member.dto.response;

import com.mju.capstone.auth.repository.entity.Role;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 조회 응답")
public record MemberInfoResponse(
        Long memberId,
        String email,
        Role role,
        String nickName,
        int birth,
        int height,
        int weight,
        int level,
        int dietLevel) {

    public static MemberInfoResponse from(Long memberId, String email, Role role, String nickName, int birth, int height, int weight, int level, int dietLevel) {
        return new MemberInfoResponse(memberId, email, role, nickName, birth, height, weight, level, dietLevel);
    }
}
