package com.mju.capstone.member.dto.request;

import com.mju.capstone.member.entity.Gender;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberUpdateRequest(
    Optional<String> password,
    Optional<String> nickname,
    Optional<Integer> height,
    Optional<Integer> weight,
    Optional<Gender> gender,
    Optional<Integer> birth,
    Optional<Integer> level,
    Optional<Integer> dietPlan
) {
}
