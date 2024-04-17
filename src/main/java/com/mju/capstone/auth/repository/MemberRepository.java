package com.mju.capstone.auth.repository;

import com.mju.capstone.auth.repository.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

  Optional<Member> findByEmail(String email);

  boolean existsByEmail(String email);

}
