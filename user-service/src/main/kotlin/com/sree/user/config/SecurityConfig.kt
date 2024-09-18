package com.sree.user.config

import com.sree.user.dto.BasicAuthCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

/**
 * This class is used to handle the security of the application.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    /**
     * This method is used to handle the security of the application.
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{
                csrf -> csrf.disable()
            }  // Disable CSRF for simplicity (enable it in production)
            .authorizeHttpRequests {
                auth ->
                auth.requestMatchers("/v2/api-docs").permitAll()
                auth.requestMatchers("/v3/api-docs").permitAll()
                auth.requestMatchers("/v3/api-docs/**").permitAll()
                auth.requestMatchers("/swagger-resources").permitAll()
                auth.requestMatchers("/swagger-resources/**").permitAll()
                auth.requestMatchers("/swagger-ui/**").permitAll()
                auth.requestMatchers("/webjars/**").permitAll()
                auth.requestMatchers("/swagger-ui.html").permitAll()
                    .anyRequest().authenticated()
            } // specific requests to allow and other to authenticate.
            .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic Authentication
        return http.build()
    }

    /**
     * This method will read properties from application.yml file and create BasicAuthCredentials object.
     */
    @Bean
    fun basicAuthCredentials(): BasicAuthCredentials {
        return BasicAuthCredentials()
    }

    /**
     * This method will set the basic credentials to application.
     */
    @Bean
    fun userDetailsService(): UserDetailsService {
        val credentials = basicAuthCredentials()
        val user = User.withUsername(credentials.name)
            .password(passwordEncoder().encode(credentials.password))  // Using BCrypt for encoding passwords
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)  // In-memory authentication
    }

    /**
     * This method is used to encode and decode the password.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()  // Password encoder using BCrypt
    }

    /**
     * This method will create AuthenticationProvider object.
     */
    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }
}
