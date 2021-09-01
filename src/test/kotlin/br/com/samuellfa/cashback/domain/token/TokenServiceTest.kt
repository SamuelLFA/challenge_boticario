package br.com.samuellfa.cashback.domain.token

import br.com.samuellfa.cashback.domain.reseller.Reseller
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.Authentication

@ExtendWith(MockitoExtension::class)
class TokenServiceTest {

    @Mock
    lateinit var authenticationMock: Authentication

    @Test
    fun `when authentication is success should return token`(){
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val reseller = Reseller(
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        `when`(authenticationMock.principal).thenReturn(reseller)
        val tokenService = TokenService()
        tokenService.secret = "a21ac12@#33@"
        tokenService.expiration = "4800"

        val token = tokenService.createToken(authenticationMock)

        assertThat(token)
            .isNotNull
    }

    @Test
    fun `when token is valid should return true`(){
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val reseller = Reseller(
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        `when`(authenticationMock.principal).thenReturn(reseller)
        val tokenService = TokenService()
        tokenService.secret = "a21ac12@#33@"
        tokenService.expiration = "4800"

        val token = tokenService.createToken(authenticationMock)

        val isValid = tokenService.isValidToken(token)

        assertTrue(isValid)
    }

    @Test
    fun `when token is valid should return id`(){
        val expected = 1L
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val reseller = Reseller(
            id = expected,
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        `when`(authenticationMock.principal).thenReturn(reseller)
        val tokenService = TokenService()
        tokenService.secret = "a21ac12@#33@"
        tokenService.expiration = "4800"

        val token = tokenService.createToken(authenticationMock)

        val id = tokenService.getUserId(token)

        assertEquals(id, expected)
    }

    @Test
    fun `when token is invalid should return false`(){
        val tokenService = TokenService()
        tokenService.secret = "a21ac12@#33@"
        tokenService.expiration = "4800"

        val isValid = tokenService.isValidToken("213121312")

        assertFalse(isValid)
    }
}