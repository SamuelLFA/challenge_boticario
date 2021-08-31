package br.com.samuellfa.cashback.application.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginRequestDTO (
    @JsonProperty("email")
    val email: String? = null,

    @JsonProperty("senha")
    val password: String? = null
) {
    fun mapToUsernamePasswordAuthenticationToken(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }
}