package com.mju.capstone.recommend.config;


import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.AssistantsClientBuilder;
import com.azure.ai.openai.assistants.AssistantsServiceVersion;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantCreationOptions;
import com.azure.ai.openai.assistants.models.CreateFileSearchToolResourceOptions;
import com.azure.ai.openai.assistants.models.CreateFileSearchToolResourceVectorStoreOptions;
import com.azure.ai.openai.assistants.models.CreateFileSearchToolResourceVectorStoreOptionsList;
import com.azure.ai.openai.assistants.models.CreateToolResourcesOptions;
import com.azure.ai.openai.assistants.models.FileDetails;
import com.azure.ai.openai.assistants.models.FilePurpose;
import com.azure.ai.openai.assistants.models.FileSearchToolDefinition;
import com.azure.ai.openai.assistants.models.OpenAIFile;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AzureConfig {

  private final ResourceLoader resourceLoader;
  @Value("${azure.credential}")
  private String credentialKey;
  @Value("${azure.endpoint}")
  private String endpoint;
  @Value("${azure.key}")
  private String key;
  @Value("${azure.model}")
  private String model;

  @Bean
  AssistantsClient assistantsClient() {
    return new AssistantsClientBuilder()
        .credential(new AzureKeyCredential(key))
        .serviceVersion(AssistantsServiceVersion.getLatest())
        .endpoint(endpoint)
        .buildClient();
  }

  @Bean
  public Assistant customAssistant(AssistantsClient client) throws IOException {
    // ResourceLoader를 사용하여 리소스를 가져옵니다.
    Path filePath = Paths.get("src", "main", "resources", "menu_final.txt");
    BinaryData fileData = BinaryData.fromFile(filePath);
    FileDetails fileDetails = new FileDetails(fileData, "meni_final.txt");
    OpenAIFile openAIFile = client.uploadFile(fileDetails, FilePurpose.ASSISTANTS);

    String instructions = loadInstructionsFromFile("instruction.txt");
    log.info("Application Started with Instructions: {}", instructions);

    CreateToolResourcesOptions createToolResourcesOptions = new CreateToolResourcesOptions();
    createToolResourcesOptions.setFileSearch(
        new CreateFileSearchToolResourceOptions(
            new CreateFileSearchToolResourceVectorStoreOptionsList(
                Arrays.asList(new CreateFileSearchToolResourceVectorStoreOptions(
                    Arrays.asList(openAIFile.getId()))))));

    return client.createAssistant(
        new AssistantCreationOptions(model)
            .setName("영양사")
            .setInstructions(instructions)
            .setTools(Arrays.asList(new FileSearchToolDefinition()))
            .setToolResources(createToolResourcesOptions)
    );
  }

  private String loadInstructionsFromFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)),
        StandardCharsets.UTF_8))) {
      return reader.lines().collect(Collectors.joining("\n"));
    }
  }
}
