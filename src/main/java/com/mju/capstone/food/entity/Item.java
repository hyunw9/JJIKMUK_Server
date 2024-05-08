package com.mju.capstone.food.entity;

import com.mju.capstone.food.exception.IllegalArgumentException;
import com.mju.capstone.global.baseEntity.BaseTimeEntity;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Item extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private String name;

  private int amount;

  private int serving;

  private int kcal;

  private int carbohydrate;

  private int protein;

  private int fat;

  private String fileName;

  public Item(Member member, String name, int amount, int serving, int kcal, int carbohydrate,
      int protein, int fat, String fileName) {
    this.member = member;
    this.name = name;
    this.amount = amount;
    this.serving = serving;
    this.kcal = kcal;
    this.carbohydrate = carbohydrate;
    this.protein = protein;
    this.fat = fat;
    this.fileName = fileName;
  }

  public static Item createItem(Member member, String name, int amount, int serving, int kcal, int carbohydrate, int protein, int fat, String imgUrl){
    validateName(name);
    return new Item(member,name, amount,serving,kcal, carbohydrate,protein,fat,imgUrl);
  }

  private static void validateName(String name) {
    if(name.length() > 255) throw new IllegalArgumentException(ErrorMessage.ARGUMENT_EXCEED_EXCEPTION);
  }
}
