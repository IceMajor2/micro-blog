package com.demo.blog.blogpost.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class OAuth2AuthorizationConfig {

    @Bean
    JdbcOAuth2AuthorizationConsentService jdbcOAuth2AuthorizationConsentService
            (JdbcOperations jdbcOperations, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, clientRepository);
    }

    @Bean
    JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService
            (JdbcOperations jdbcOperations, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, clientRepository);
    }
}
