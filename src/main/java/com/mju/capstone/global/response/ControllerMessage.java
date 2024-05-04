package com.mju.capstone.global.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mju.capstone.global.response.message.ErrorMessage;
import com.mju.capstone.global.response.message.SuccessMessage;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ControllerMessage<T>(

    int status,
    String message,
    Optional<T> data
) {

  public static <T> ControllerMessage<T> of(SuccessMessage successMessage) {
    return new ControllerMessage(successMessage.getStatus(), successMessage.getMessage(), Optional.empty());
  }

  public static <T>ControllerMessage<T> of(SuccessMessage successMessage, T data) {
    return new ControllerMessage(successMessage.getStatus(), successMessage.getMessage(), Optional.of(data));
  }

  public static <T>ControllerMessage<T> of(ErrorMessage errorMessage) {
    return new ControllerMessage(errorMessage.getStatus(), errorMessage.getMessage(), Optional.empty());
  }
}
