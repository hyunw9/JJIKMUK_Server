package com.mju.capstone.food.service;

import com.mju.capstone.food.dto.request.ItemCreateRequest;
import com.mju.capstone.food.dto.response.ItemResponse;
import com.mju.capstone.food.dto.response.TotalNutritionResponse;
import com.mju.capstone.food.entity.Item;
import com.mju.capstone.food.event.HistoryEvent;
import com.mju.capstone.food.repository.ItemRepository;
import com.mju.capstone.global.exception.NotFoundException;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.service.MemberService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ApplicationEventPublisher publisher;
  private final MemberService memberService;

  @Transactional
  public TotalNutritionResponse uploadItem(List<ItemCreateRequest> request) {

    String email = SecurityUtil.getLoginUserEmail();
    Member member = memberService.findByEmail(email);

    //item 은 히스토리 안가져도 됨.

    List<Item> itemList = request.stream().map(
            item -> Item.createItem(member, item.name(), item.amount(), item.serving(), item.kcal(),
                item.carbohydrate(), item.protein(), item.fat(), item.fileName()))
        .collect(Collectors.toUnmodifiableList());
    itemRepository.saveAll(itemList);

    HistoryEvent event = new HistoryEvent(this, member.getEmail(), itemList);
    //영양소 합계 전파
    publisher.publishEvent(event);

    return TotalNutritionResponse.of(event.getTotalKcal(), event.getTotalCarbohydrate(),
        event.getTotalProtein(), event.getTotalFat());
  }

  public List<ItemResponse> findItemHistoryByDate(LocalDate date) {
    Member member = memberService.findByEmail(SecurityUtil.getLoginUserEmail());

    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.atTime(LocalTime.MAX);

    List<Item> itemList = findByMemberAndDateTime(member, start, end);

    return itemList.stream().map(item ->
        ItemResponse.of(item.getId(), item.getName(), item.getKcal(), item.getCarbohydrate(),
            item.getProtein(), item.getFat(), item.getFileName())
    ).collect(Collectors.toUnmodifiableList());
  }

  private List<Item> findByMemberAndDateTime(Member member, LocalDateTime start,
      LocalDateTime end) {
    return itemRepository.findItemByMemberAndLocalDateTimeBetween(member, start, end).orElseThrow(
        () -> new NotFoundException(ErrorMessage.ARGUMENT_NOT_FOUND)
    );
  }

  public List<ItemResponse> searchItemsByString(String itemName) {

    List<Item> foundItemList = itemRepository.findAllByNameContains(itemName);
    return foundItemList.stream().map(
            item -> ItemResponse.of(item.getId(), item.getName(), item.getKcal(),
                item.getCarbohydrate(), item.getProtein(), item.getFat(), item.getFileName()))
        .collect(Collectors.toUnmodifiableList());
  }
}
