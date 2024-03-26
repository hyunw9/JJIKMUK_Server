package com.mju.capstone.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/")
  public String returnTest(){
    return "THIS IS METHOD FOR TEST DEPLOYMENT";
  }

}
