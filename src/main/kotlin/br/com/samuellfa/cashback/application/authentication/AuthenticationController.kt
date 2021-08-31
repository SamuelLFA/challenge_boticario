package br.com.samuellfa.cashback.application.authentication

import br.com.samuellfa.cashback.domain.token.TokenService
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/login")
@Profile(value = ["prod", "test"])
class AuthenticationController(
    val tokenService: TokenService,
    val authManager: AuthenticationManager
) {

    @PostMapping
    fun authenticate(@Valid @RequestBody form: LoginRequestDTO): ResponseEntity<TokenResponseDTO> {
        val loginData = form.mapToUsernamePasswordAuthenticationToken()
        return try {
            val authentication = authManager.authenticate(loginData)
            val token = tokenService.createToken(authentication)
            ResponseEntity.ok(TokenResponseDTO(token, "Bearer"))
        } catch (e: AuthenticationException) {
            ResponseEntity.badRequest().build()
        }
    }
}
