package com.sree.user.dto

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * This class read properties from application.yml.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.user")
class BasicAuthCredentials {
    @NotBlank
    var name: String = ""
    @NotBlank
    var password: String = ""
}