package br.com.samuellfa.cashback.application.authentication

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponseDTO (

    @JsonProperty("token")
    val token: String,

    @JsonProperty("tipo")
    val type: String
)