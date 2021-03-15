package com.utusikov.hw3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.utusikov.hw3")
public class AppConfiguration {
}
