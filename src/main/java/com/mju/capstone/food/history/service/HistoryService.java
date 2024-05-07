package com.mju.capstone.food.history.service;

import com.mju.capstone.food.history.dto.HistoryResponse;
import com.mju.capstone.food.history.entity.History;
import com.mju.capstone.food.history.repository.HistoryRepository;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.service.MemberService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final MemberService memberService;

  public HashMap<Long, HistoryResponse> findRecentHistory() {

    String email = SecurityUtil.getLoginUserEmail();
    Member member = memberService.findByEmail(email);

    LocalDateTime nowDate = LocalDateTime.now();
    LocalDateTime sevenDaysAgoDate = nowDate.minusDays(7);

    List<History> historyList = historyRepository.findByMemberIdAndLocalDateTimeBetween(
        member.getId(), sevenDaysAgoDate, nowDate);

    HashMap<Long, HistoryResponse> historyMap = new HashMap<>();
    for (History history : historyList) {
      Duration duration = Duration.between(history.getLocalDateTime(), nowDate);
      HistoryResponse response = HistoryResponse.of(history.getId(), history.getTot_kcal(),
          history.getTot_carbohydrate(), history.getTot_protein(), history.getTot_fat());
      historyMap.put(duration.toDays(),response);
    }

    for (long i = 0; i <=7; i++){
      historyMap.putIfAbsent(i,HistoryResponse.defaultInstance(null));
    }

    return historyMap;
  }

}
