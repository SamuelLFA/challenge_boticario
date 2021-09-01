package br.com.samuellfa.cashback.domain.reseller

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class ResellerServiceTest {

    @Mock
    lateinit var resellerRepositoryMock: ResellerRepository

    @Mock
    lateinit var passwordEncoderMock: PasswordEncoder

    @Test
    fun `when data is ok should save on database`(){
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val passwordHashed = "sjhdsdsa21@321@#4"
        val reseller = Reseller(
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        val resellerService = ResellerService(resellerRepositoryMock, passwordEncoderMock)
        `when`(passwordEncoderMock.encode(password)).thenReturn(passwordHashed)

        resellerService.saveReseller(reseller)

        verify(passwordEncoderMock, times(1)).encode(password)
        verify(resellerRepositoryMock, times(1)).save(reseller.copy(passwordHashed = passwordHashed))
    }

    @Test
    fun `when reseller exists and get by document should return reseller`(){
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val expected = Reseller(
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        val resellerService = ResellerService(resellerRepositoryMock, passwordEncoderMock)
        `when`(resellerRepositoryMock.getByDocument(document)).thenReturn(expected)

        val reseller = resellerService.getResellerByDocument(document)

        assertThat(reseller)
            .usingRecursiveComparison()
            .isEqualTo(expected)
        verify(resellerRepositoryMock, times(1)).getByDocument(document)
    }

    @Test
    fun `when reseller not exists and get by document should return null`(){
        val document = "document"
        val resellerService = ResellerService(resellerRepositoryMock, passwordEncoderMock)
        `when`(resellerRepositoryMock.getByDocument(document)).thenReturn(null)

        val reseller = resellerService.getResellerByDocument(document)

        assertThat(reseller)
            .isNull()
        verify(resellerRepositoryMock, times(1)).getByDocument(document)
    }

    @Test
    fun `when reseller exist and get by email should return reseller`(){
        val email = "email"
        val document = "document"
        val name = "name"
        val password = "1231421321312"
        val expected = Reseller(
            email = email,
            document = document,
            name = name,
            passwordHashed = password
        )
        val resellerService = ResellerService(resellerRepositoryMock, passwordEncoderMock)
        `when`(resellerRepositoryMock.getByEmail(email)).thenReturn(expected)

        val reseller = resellerService.getResellerByEmail(email)

        assertThat(reseller)
            .usingRecursiveComparison()
            .isEqualTo(expected)
        verify(resellerRepositoryMock, times(1)).getByEmail(email)
    }

    @Test
    fun `when reseller not exists and get by email should return null`(){
        val email = "email"
        val resellerService = ResellerService(resellerRepositoryMock, passwordEncoderMock)
        `when`(resellerRepositoryMock.getByEmail(email)).thenReturn(null)

        val reseller = resellerService.getResellerByEmail(email)

        assertThat(reseller)
            .isNull()
        verify(resellerRepositoryMock, times(1)).getByEmail(email)
    }
}