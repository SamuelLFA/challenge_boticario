package br.com.samuellfa.cashback.domain.order

import br.com.samuellfa.cashback.domain.reseller.Reseller
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val code: String,
    val price: BigDecimal,
    val orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status = Status.VALIDATING,
    @ManyToOne
    val reseller: Reseller? = null
) {
    val cashbackPer: Int
        get() {
            return when {
                price <= BigDecimal("1000.00") -> 10
                price <= BigDecimal("1500.00") -> 15
                else -> 20
            }
        }

    val cashbackValue: BigDecimal
        get() {
            val cashbackPer = cashbackPer
            return price.multiply(BigDecimal(cashbackPer)).divide(BigDecimal("100.00"), RoundingMode.CEILING)
        }

}