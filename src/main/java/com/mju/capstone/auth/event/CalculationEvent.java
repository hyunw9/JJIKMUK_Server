package com.mju.capstone.auth.event;

import com.mju.capstone.member.dto.request.NutritionCalculatorRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CalculationEvent extends ApplicationEvent {

  private final NutritionCalculatorRequest request;

  public CalculationEvent(Object source, NutritionCalculatorRequest request) {
    super(source);
    this.request = request;
  }

}
