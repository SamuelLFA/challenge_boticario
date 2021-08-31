package br.com.samuellfa.cashback.domain.reseller

import org.springframework.data.jpa.repository.JpaRepository

interface ResellerRepository : JpaRepository<Reseller, Long> {

    fun getByDocument(document: String) : Reseller?
    fun getByEmail(email: String) : Reseller?
}