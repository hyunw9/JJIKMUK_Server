package com.mju.capstone.recommend.domain;

import java.util.List;

public interface GptManager {

  List<String> sendOpenAIRequest(String prompt);

}
