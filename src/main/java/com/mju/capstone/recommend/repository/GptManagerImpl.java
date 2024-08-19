package com.mju.capstone.recommend.repository;

import static com.azure.ai.openai.assistants.models.MessageRole.USER;

import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantThread;
import com.azure.ai.openai.assistants.models.AssistantThreadCreationOptions;
import com.azure.ai.openai.assistants.models.CreateRunOptions;
import com.azure.ai.openai.assistants.models.MessageContent;
import com.azure.ai.openai.assistants.models.MessageTextContent;
import com.azure.ai.openai.assistants.models.PageableList;
import com.azure.ai.openai.assistants.models.RunStatus;
import com.azure.ai.openai.assistants.models.ThreadMessage;
import com.azure.ai.openai.assistants.models.ThreadMessageOptions;
import com.azure.ai.openai.assistants.models.ThreadRun;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.capstone.recommend.domain.GptManager;
import com.mju.capstone.recommend.dto.response.Menu;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
@Slf4j
public class GptManagerImpl implements GptManager {

  private final AssistantsClient client;
  private final Assistant assistant;

  public List<Menu> sendOpenAIRequest(String messageContent) {
    log.info("Processing message: {}", messageContent);

    AssistantThread thread = createAssistantThread();
    sendMessageToThread(thread.getId(), messageContent);

    List<Menu> result;

    try {
      result = getGptResponse(thread.getId());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  private AssistantThread createAssistantThread() {
    return client.createThread(new AssistantThreadCreationOptions());
  }

  private void sendMessageToThread(String threadId, String messageContent) {
    client.createMessage(threadId, new ThreadMessageOptions(USER, messageContent));
  }

  private List<Menu> getGptResponse(String threadId) throws InterruptedException {
    ThreadRun run = client.createRun(threadId, new CreateRunOptions(assistant.getId()));

    do {
      run = client.getRun(run.getThreadId(), run.getId());
      Thread.sleep(500);
    } while (run.getStatus() == RunStatus.QUEUED || run.getStatus() == RunStatus.IN_PROGRESS);

    return extractMessagesFromResponse(client.listMessages(run.getThreadId()));
  }

  private List<Menu> extractMessagesFromResponse(PageableList<ThreadMessage> messages) {
    List<Menu> result = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    ThreadMessage threadMessage = messages.getData().getFirst();

    for (MessageContent messageContent : threadMessage.getContent()) {
      String jsonResponse = ((MessageTextContent) messageContent).getText().getValue();
      log.info("Message content: {}", jsonResponse);
      try {
        jsonResponse = jsonResponse.replaceAll("```json", "").trim();
        List<Menu> menu = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        result.addAll(menu);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    log.info("result: {}" ,result.toString());
    return result;
  }
}
