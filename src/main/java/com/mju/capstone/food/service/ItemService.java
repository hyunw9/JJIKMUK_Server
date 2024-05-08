package com.mju.capstone.food.service;

import com.mju.capstone.food.dto.request.ItemCreateRequest;
import com.mju.capstone.food.dto.response.ItemResponse;
import com.mju.capstone.food.entity.Item;
import com.mju.capstone.food.event.HistoryEvent;
import com.mju.capstone.food.repository.ItemRepository;
import com.mju.capstone.global.security.util.SecurityUtil;
import com.mju.capstone.member.entity.Member;
import com.mju.capstone.member.service.MemberService;
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
  public ItemResponse uploadItem(ItemCreateRequest request) {

    String email = SecurityUtil.getLoginUserEmail();
    Member member = memberService.findByEmail(email);

    //item 은 히스토리 안가져도 됨.

    Item item = itemRepository.save(Item.createItem(member, request.name(),
        request.amount(), request.serving(), request.kcal(), request.carbohydrate(),
        request.protein(), request.fat(), request.fileName()));

    publisher.publishEvent(
        new HistoryEvent(this, email, item.getKcal(), item.getCarbohydrate(),
            item.getProtein(), item.getFat()));

    return ItemResponse.of(item.getId(), item.getName(), item.getKcal(), item.getCarbohydrate(),
        item.getProtein(),
        item.getFat(), item.getFileName());
  }

}
