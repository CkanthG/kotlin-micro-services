package com.sree.claims.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

/**
 * This configuration class enable load balancing and create an instance of RestClient.
 */
@Configuration
class ApplicationConfig {
    @Bean
    @LoadBalanced
    fun restClient(): RestClient.Builder {
        return RestClient.builder()
    }
}