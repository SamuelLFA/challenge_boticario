package br.com.samuellfa.cashback.application.cashback

import com.fasterxml.jackson.annotation.JsonProperty

data class ExternalAPITotalDTO (

    @JsonProperty("credit")
    val credit: Int
)