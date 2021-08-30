package br.com.samuellfa.cashback.application.order

import br.com.samuellfa.cashback.domain.order.Order
import br.com.samuellfa.cashback.domain.reseller.ResellerService
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class OrderPostRestRequest (

        @JsonProperty("codigo")
        val code: @NotNull @NotEmpty @Length(min = 1, max = 50) String? = null,

        @JsonProperty("valor")
        val price: @NotNull @PositiveOrZero BigDecimal? = null,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        @JsonProperty("data")
        val orderDate: @NotNull LocalDateTime? = null,

        @JsonProperty("cpf")
        val document: @CPF String? = null
) {
    fun toEntity(resellerService: ResellerService): Order =
        resellerService.getResellerByDocument(document!!)
            ?.let { Order(
                code = code!!,
                price = price!!,
                orderDate = orderDate!!,
                reseller = it
            ) }
            ?: run {
                throw IllegalArgumentException("Reseller not found")
            }
}