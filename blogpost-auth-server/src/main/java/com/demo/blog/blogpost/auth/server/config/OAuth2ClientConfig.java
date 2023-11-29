package com.demo.blog.blogpost.auth.server.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;
import java.util.UUID;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    ApplicationRunner clientRunner(RegisteredClientRepository clientRepository) {
        return args -> {
            final String clientId = "myclient";
            if(clientRepository.findByClientId(clientId) == null) {
                clientRepository.save(
                        RegisteredClient
                                .withId(UUID.randomUUID().toString())
                                .clientId(clientId)
                                .clientSecret("{bcrypt}$2a$10$EbXaUVnjrkKoixjefxT5N.PQ2QRSStEv7ZJlP0zIOYu5T8rE0FulO")
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(Set.of(
                                        AuthorizationGrantType.CLIENT_CREDENTIALS,
                                        AuthorizationGrantType.AUTHORIZATION_CODE,
                                        AuthorizationGrantType.REFRESH_TOKEN
                                )))
                                .redirectUri("http://127.0.0.1:9000/login/oauth2/code/spring")
                                .scopes(scopes -> scopes.addAll(Set.of(
                                        "user.read", "user.write", OidcScopes.OPENID
                                )))
                                .clientSettings(ClientSettings.builder()
                                        .requireAuthorizationConsent(true)
                                        .build())
                                .build()
                );
            }
        };
    }
}
