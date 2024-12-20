package org.example.notionegateway.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository(private val authenticationManager: AuthenticationManager) :
    ServerSecurityContextRepository {

    override fun save(swe: ServerWebExchange?, sc: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext> {
        val stringMono = Mono.justOrEmpty(swe.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
        return stringMono.flatMap { token: String -> this.getSecurityContext(token) }
    }

    private fun getSecurityContext(token: String): Mono<out SecurityContext> {
        val auth: Authentication = UsernamePasswordAuthenticationToken(token, token)
        return authenticationManager.authenticate(auth).map { e -> SecurityContextImpl(e) }
    }
}