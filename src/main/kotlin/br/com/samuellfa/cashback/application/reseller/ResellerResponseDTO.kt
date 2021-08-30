package br.com.samuellfa.cashback.application.reseller

import br.com.samuellfa.cashback.domain.reseller.Reseller

data class ResellerResponseDTO (

    var name: String,
    val document: String,
    val email: String
) {
    constructor(reseller: Reseller) : this(
        name = reseller.name,
        document = reseller.document,
        email = reseller.email
    )
}
