package br.com.samuellfa.cashback.application.order

import br.com.samuellfa.cashback.application.reseller.ResellerResponseDTO
import br.com.samuellfa.cashback.domain.order.Order
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponseDTO (

    @JsonProperty("codigo")
    val code: String,

    @JsonProperty("valor")
    val price: BigDecimal,

    @JsonProperty("data")
    val orderDate: LocalDateTime,

    @JsonProperty("revendedor")
    val resellerDto: ResellerResponseDTO,

    @JsonProperty("porcentagem_cashback")
    val cashbackPer: Int,

    @JsonProperty("valor_cashback")
    val cashbackValue: BigDecimal,

    @JsonProperty("status")
    val status: String
) {
    constructor(order: Order) : this(
        code = order.code,
        price = order.price,
        orderDate = order.orderDate,
        resellerDto = ResellerResponseDTO(order.reseller!!),
        cashbackPer = order.cashbackPer,
        cashbackValue = order.cashbackValue,
        status = order.status.name
    )
}
