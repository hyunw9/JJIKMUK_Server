package com.mju.capstone.food.service;

import com.mju.capstone.food.dto.response.HistoryResponse;
import com.mju.capstone.food.entity.History;
import com.mju.capstone.food.event.HistoryEvent;
import com.mju.capstone.food.repository.HistoryRepository;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.repository.MemberRepository;
import com.mju.capstone.member.service.MemberService;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
@Slf4j
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final MemberService memberService;
  private final MemberRepository memberRepository;

  public HashMap<Integer, HistoryResponse> findRecentHistory() {

    String email = SecurityUtil.getLoginUserEmail();
    Member member = memberService.findByEmail(email);

    LocalDate nowDate = LocalDate.now();
    LocalDate sevenDaysAgoDate = nowDate.minusDays(7);

    List<History> historyList = historyRepository.findByMemberAndLocalDateBetween(
        member, sevenDaysAgoDate, nowDate);

    HashMap<Integer, HistoryResponse> historyMap = new HashMap<>();
    for (History history : historyList) {
      Period period = Period.between(history.getLocalDate(), nowDate);
      HistoryResponse response = HistoryResponse.of(history.getId(), history.getTot_kcal(),
          history.getTot_carbohydrate(), history.getTot_protein(), history.getTot_fat());
      historyMap.put(period.getDays(), response);
    }

    for (int i = 0; i <= 7; i++) {
      historyMap.putIfAbsent(i, HistoryResponse.defaultInstance(null));
    }

    return historyMap;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateHistory(HistoryEvent historyEvent) {

    Member member = memberService.findByEmail(historyEvent.getEmail());

    LocalDate now = LocalDate.now();

    History history = historyRepository.findByMemberAndLocalDate(member, now);

    if (history == null) {
      history = new History(member);
      historyRepository.save(history);
    }
    history.updateHistory(historyEvent);
  }
}
