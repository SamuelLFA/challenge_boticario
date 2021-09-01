package br.com.samuellfa.cashback.domain.authentication

import br.com.samuellfa.cashback.domain.reseller.Reseller
import br.com.samuellfa.cashback.domain.reseller.ResellerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AuthenticationServiceTest {

    @Mock
    lateinit var resellerServiceMock: ResellerService

    @Test
    fun `when exist user for the email should return user`(){
        val id = 1L
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val expected = Reseller(
            id = id,
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        val authenticationService = AuthenticationService(resellerServiceMock)
        `when`(resellerServiceMock.getResellerByEmail(email)).thenReturn(expected)

        val reseller = authenticationService.loadUserByUsername(email)

        Assertions.assertThat(reseller)
            .usingRecursiveComparison()
            .isEqualTo(expected)
        verify(resellerServiceMock, times(1)).getResellerByEmail(email)
    }
}