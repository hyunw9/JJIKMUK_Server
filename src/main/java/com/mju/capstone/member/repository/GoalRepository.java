package com.mju.capstone.member.repository;

import com.mju.capstone.member.entity.Goal;
import com.mju.capstone.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Long> {

  Optional<Goal> findByMember(Member member);

  boolean existsByMember(Member member);
}
