package br.com.samuellfa.cashback.application.reseller

import br.com.samuellfa.cashback.domain.reseller.Reseller
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ResellerPostRequestDTO (

    @field:NotNull @field:NotEmpty @field:Length(min = 1, max = 50)
    @field:JsonProperty("nome_completo")
    val name: String? = null,

    @field:JsonProperty("cpf")
    @field:NotNull @field:NotEmpty @field:CPF
    val document:  String? = null,

    @field:JsonProperty("email")
    @field:NotNull @field:NotEmpty @field:Email
    val email: String? = null,

    @field:JsonProperty("senha")
    @field:NotNull @field:NotEmpty @field:Length(min = 6, max = 30)
    val password: String? = null,
) {
    fun toEntity() : Reseller {
        return Reseller (
            name = name!!,
            document = document!!,
            email = email!!,
            passwordHashed = password!!
        )
    }
}