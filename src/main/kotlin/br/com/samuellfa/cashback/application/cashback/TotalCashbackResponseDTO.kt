package br.com.samuellfa.cashback.application.cashback

import br.com.samuellfa.cashback.application.reseller.ResellerResponseDTO
import br.com.samuellfa.cashback.domain.reseller.Reseller
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class TotalCashbackResponseDTO (

    @JsonProperty("revendedor")
    val resellerResponseDTO: ResellerResponseDTO,

    @JsonProperty("cashback_acumulado")
    val totalCashback: BigDecimal
) {

    constructor(reseller: Reseller, externalAPIResponseDTO: ExternalAPIResponseDTO):
        this(
            ResellerResponseDTO(reseller),
            totalCashback = BigDecimal(externalAPIResponseDTO.total.credit)
        )
}
