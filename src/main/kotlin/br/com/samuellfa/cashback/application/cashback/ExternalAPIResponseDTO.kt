package br.com.samuellfa.cashback.application.cashback

import com.fasterxml.jackson.annotation.JsonProperty

data class ExternalAPIResponseDTO (

    @JsonProperty("statusCode")
    val statusCode: Int,

    @JsonProperty("body")
    val total: ExternalAPITotalDTO
)