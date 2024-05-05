package com.mju.capstone.food.history.repository;

import com.mju.capstone.food.history.entity.History;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

  List<History> findByMemberIdAndLocalDateTimeBetween(Long memberId, LocalDateTime start, LocalDateTime end);
}
