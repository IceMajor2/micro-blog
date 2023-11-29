package com.demo.blog.blogpost.auth.client.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb) {
        // TODO: configure
        return rlb.routes()
                .route(rs -> rs.path("/welcome")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://localhost:8080/welcome"))
                .build();
    }
}