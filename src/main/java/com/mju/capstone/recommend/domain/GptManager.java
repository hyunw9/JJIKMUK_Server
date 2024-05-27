package com.mju.capstone.recommend.domain;

import com.mju.capstone.recommend.dto.response.RecommendResponse;

public interface GptManager {

  RecommendResponse sendOpenAIRequest(String prompt);

}
