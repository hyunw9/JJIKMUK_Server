package com.mju.capstone.global.security.token.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class RefreshToken {

  @Id
  @Column(name ="RT_KEY")
  private String key;  //member id

  @Column(name = "RT_VALUE")
  private String value; // token value

  public RefreshToken updateValue(String token){
    this.value = token;
    return this;
  }
}
