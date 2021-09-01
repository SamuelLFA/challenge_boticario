package br.com.samuellfa.cashback.domain.order

import br.com.samuellfa.cashback.domain.reseller.Reseller
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderTest {

    @Test
    fun `when price is less or equal than 1000 per cashback should be 10`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("1000.00")
        )

        assertEquals(expected.cashbackPer, 10)
        assertEquals(expected.cashbackValue, BigDecimal("100.00"))
    }

    @Test
    fun `when price is less than 1000 per cashback should be 10`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("990.00")
        )

        assertEquals(expected.cashbackPer, 10)
        assertEquals(expected.cashbackValue, BigDecimal("99.00"))
    }

    @Test
    fun `when price is greater than 1000 and less or equal than 1500 per cashback should be 15`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("1500.00")
        )

        assertEquals(expected.cashbackPer, 15)
        assertEquals(expected.cashbackValue, BigDecimal("225.00"))
    }

    @Test
    fun `when price is greater than 1000 and less than 1500 per cashback should be 15`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("1300.00")
        )

        assertEquals(expected.cashbackPer, 15)
        assertEquals(expected.cashbackValue, BigDecimal("195.00"))
    }

    @Test
    fun `when price is greater than 1500 per cashback should be 20`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("2100.00")
        )

        assertEquals(expected.cashbackPer, 20)
        assertEquals(expected.cashbackValue, BigDecimal("420.00"))
    }
}