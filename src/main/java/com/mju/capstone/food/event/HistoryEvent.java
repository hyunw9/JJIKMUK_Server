package com.mju.capstone.food.event;

import com.mju.capstone.food.entity.Item;
import java.util.List;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HistoryEvent extends ApplicationEvent {

  private final String email;
  private final int totalKcal;
  private final int totalCarbohydrate;
  private final int totalProtein;
  private final int totalFat;

  public HistoryEvent(Object source, String email, int totalKcal, int totalCarbohydrate, int totalProtein,
      int totalFat) {
    super(source);
    this.email = email;
    this.totalKcal = totalKcal;
    this.totalCarbohydrate = totalCarbohydrate;
    this.totalProtein = totalProtein;
    this.totalFat = totalFat;
  }

  public HistoryEvent(Object source,String email, List<Item> itemList){
    super(source);
    this.email = email;
    this.totalKcal= itemList.stream().mapToInt(Item::getKcal).sum();
    this.totalCarbohydrate=itemList.stream().mapToInt(Item::getCarbohydrate).sum();
    this.totalProtein=itemList.stream().mapToInt(Item::getProtein).sum();
    this.totalFat=itemList.stream().mapToInt(Item::getFat).sum();
  }
}
