package com.mju.capstone.food.repository;

import com.mju.capstone.food.entity.Item;
import com.mju.capstone.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  Optional<List<Item>> findItemByMemberAndLocalDateTimeBetween(Member member,LocalDateTime start, LocalDateTime end);
}
