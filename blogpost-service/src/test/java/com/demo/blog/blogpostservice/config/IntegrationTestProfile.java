package com.demo.blog.blogpostservice.config;

// TODO: Fix
//@Configuration
//@Profile("integration-test")
//public class IntegrationTestProfile {
//
//    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));
//
//    static {
//        mysql.start();
//    }
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//        dynamicPropertyRegistry.add("spring.datasource.url", mysql::getJdbcUrl);
//        dynamicPropertyRegistry.add("spring.datasource.username", mysql::getUsername);
//        dynamicPropertyRegistry.add("spring.datasource.password", mysql::getPassword);
//    }
//}
