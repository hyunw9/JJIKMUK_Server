package com.mju.capstone.food.entity;

import com.mju.capstone.food.event.HistoryEvent;
import com.mju.capstone.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private int tot_kcal;

  private int tot_carbohydrate;

  private int tot_protein;

  private int tot_fat;

  private LocalDate localDate;

  private LocalDateTime modifiedAt;

  public History(Member member, int tot_kcal, int tot_carbohydrate, int tot_protein, int tot_fat) {
    this.member = member;
    this.tot_kcal = tot_kcal;
    this.tot_carbohydrate = tot_carbohydrate;
    this.tot_protein = tot_protein;
    this.tot_fat = tot_fat;
    this.localDate = LocalDate.now();
    this.modifiedAt = LocalDateTime.now();
  }

  public void updateHistory(HistoryEvent historyEvent){
    this.tot_kcal+= historyEvent.getTotalKcal();
    this.tot_carbohydrate+=historyEvent.getTotalCarbohydrate();
    this.tot_protein+=historyEvent.getTotalProtein();
    this.tot_fat+= historyEvent.getTotalFat();
  }

  public History(Member member){
    this.member = member;
    this.tot_kcal=0;
    this.tot_carbohydrate = 0;
    this.tot_protein=0;
    this.tot_fat=0;
    this.localDate=LocalDate.now();
    this.modifiedAt = LocalDateTime.now();
  }

  public static History getDefault(Member member){
    return new History(member);
  }
}
