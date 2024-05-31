package com.mju.capstone.recommend.repository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.mju.capstone.global.exception.BusinessException;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.recommend.domain.GptManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@PropertySource("classpath:application.yml")
@Slf4j
public class GptManagerImpl implements GptManager {

  private final RestTemplate restTemplate;

  @Value("${python-server.url}")
  private String server_url;

  public GptManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<String> sendOpenAIRequest(String request) {

    String url = server_url;
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("content", request);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_JSON);

    HttpEntity<Map<String, String>> requestHttpEntity = new HttpEntity<>(requestBody, headers);

//    ResponseModel response= restTemplate.postForObject(url, requestHttpEntity, ResponseModel.class);

    ResponseEntity<List<String>> responseEntity =
        restTemplate.exchange(url,HttpMethod.POST,requestHttpEntity,new ParameterizedTypeReference<List<String>>() {});

    log.info(responseEntity.getBody().toString());
    List<String> recommendResponse = responseEntity.getBody();

    if (recommendResponse.isEmpty() || recommendResponse == null) {
      throw new BusinessException(ErrorMessage.RECOMMEND_NOT_FOUND);
    }
    return recommendResponse;
  }
}
