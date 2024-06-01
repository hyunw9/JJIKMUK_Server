package com.mju.capstone.recommend.domain;

import com.mju.capstone.recommend.dto.response.Menu;

import java.util.List;

public interface GptManager {

  List<Menu> sendOpenAIRequest(String prompt);

}
