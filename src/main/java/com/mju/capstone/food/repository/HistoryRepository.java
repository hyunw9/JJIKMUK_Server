package com.mju.capstone.food.repository;

import com.mju.capstone.food.entity.History;
import com.mju.capstone.member.entity.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

  List<History> findByMemberAndLocalDateBetween(Member member, LocalDate start, LocalDate end);

  History findByMemberAndLocalDate(Member member, LocalDate today);

  History findByMember(Member member);
}
