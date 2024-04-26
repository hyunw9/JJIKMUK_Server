package com.mju.capstone.global.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.mju.capstone.global.security.filter.JwtFilter;
import com.mju.capstone.global.security.provider.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(corsCutomizer -> corsCutomizer.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setExposedHeaders(Arrays.asList("*"));
            config.setAllowCredentials(true);
            config.setMaxAge(3600L);
            return config;
          }
        }))
        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests((request) -> request
//            .requestMatchers("/**").permitAll()
                .requestMatchers("/api/v1/**", "/api/v1/auth/**").permitAll()
                // SpringDoc 경로 허용
                .requestMatchers("/api-docs/**").permitAll()
                // 추가적인 Swagger UI 관련 경로 허용 (static resources)
                .requestMatchers("/webjars/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
