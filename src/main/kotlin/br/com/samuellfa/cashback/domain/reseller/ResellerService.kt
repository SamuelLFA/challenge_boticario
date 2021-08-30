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
            password = passwordEncoder.encode(reseller.password)
        )
        resellerRepository.save(resellerWithEncodedPassword)
    }

    fun getResellerByDocument(document: String): Reseller? {
        return resellerRepository.getByDocument(document)
    }
}