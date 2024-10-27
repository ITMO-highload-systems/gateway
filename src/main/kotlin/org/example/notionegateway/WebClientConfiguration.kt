package org.example.notionegateway

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
@EnableConfigurationProperties
class WebClientConfiguration {

    @Bean
    @LoadBalanced
    fun webClient(): WebClient.Builder {
        return WebClient.builder()
    }
}