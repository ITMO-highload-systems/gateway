package org.example.notionegateway

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(private val webClient: WebClient.Builder) : ReactiveAuthenticationManager {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${app.security-server-name}")
    lateinit var securityServerName: String

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val jwtToken: String = authentication.credentials.toString()

        return tokenValidate(jwtToken)
            .flatMap { isValid ->
                if (isValid) {
                    logger.debug("Token is valid: $isValid")
                    Mono.just(
                        UsernamePasswordAuthenticationToken(
                            null, null,
                            null
                        )
                    )
                } else {
                    logger.debug("Token is invalid: $isValid")
                    Mono.error(IllegalArgumentException("Token is not valid"))
                }
            }
    }


    private fun tokenValidate(token: String): Mono<Boolean> {
        return webClient.baseUrl("http://$securityServerName").build()
            .get()
            .uri("/auth/is-token-valid/$token")
            .retrieve()
            .bodyToMono(Boolean::class.java)
    }
}