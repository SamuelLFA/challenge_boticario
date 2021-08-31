package br.com.samuellfa.cashback.domain.reseller

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ResellerService(
    val resellerRepository: ResellerRepository,
    val passwordEncoder: PasswordEncoder
) {

    fun saveReseller(reseller: Reseller) {
        val resellerWithEncodedPassword = reseller.copy(
            passwordHashed = passwordEncoder.encode(reseller.password)
        )
        resellerRepository.save(resellerWithEncodedPassword)
    }

    fun getResellerByDocument(document: String): Reseller? =
        resellerRepository.getByDocument(document)

    fun getResellerByEmail(email: String): Reseller? =
        resellerRepository.getByEmail(email)
}