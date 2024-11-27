package org.example.notionegateway.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository
) {
    @Value("\${app.security-server-name}")
    lateinit var securityServerName: String

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { c->c.disable() }
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange { auth ->
                auth
                    .pathMatchers("/actuator/**").permitAll()
                    .pathMatchers("/$securityServerName/auth/**").permitAll()
                    .pathMatchers("/*/v3/**").permitAll()
                    .pathMatchers("/swagger-ui.html").permitAll()
                    .pathMatchers("/swagger-ui/**").permitAll()
                    .pathMatchers("/v3/**").permitAll()
                    .pathMatchers("/webjars/swagger-ui/**").permitAll()
                    .anyExchange().authenticated()
            }

        return http.build()

    }
}