package br.com.samuellfa.cashback.domain.order

import br.com.samuellfa.cashback.domain.reseller.Reseller
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class OrderServiceTest {

    @Mock
    lateinit var orderRepository: OrderRepository

    @Test
    fun `when data is ok should save on database`(){
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
        val orderService = OrderService(orderRepository)
        `when`(orderRepository.save(expected)).thenReturn(expected)

        val order = orderService.saveOrder(expected)

        assertThat(order)
            .usingRecursiveComparison()
            .isEqualTo(expected)

        verify(orderRepository, times(1)).save(order)
    }

    @Test
    fun `when document is special should save on database with status approved`(){
        val reseller = Reseller(
            email = "email",
            document = "15350946056",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val orderToSave = Order(
            orderDate = LocalDateTime.now(),
            status = Status.VALIDATING,
            reseller = reseller,
            code = "12345",
            price = BigDecimal("1000.00")
        )
        val expected = orderToSave.copy(status = Status.APPROVED)
        val orderService = OrderService(orderRepository)
        `when`(orderRepository.save(expected)).thenReturn(expected)

        val order = orderService.saveOrder(orderToSave)

        assertThat(order)
            .usingRecursiveComparison()
            .isEqualTo(expected)

        verify(orderRepository, times(1)).save(expected)
    }

    @Test
    fun `when order exist and get all should return all orders`(){
        val reseller = Reseller(
            email = "email",
            document = "document",
            name = "name",
            passwordHashed = "1231421321312"
        )
        val expected = listOf(
            Order(
                orderDate = LocalDateTime.now(),
                status = Status.VALIDATING,
                reseller = reseller,
                code = "12345",
                price = BigDecimal("1000.00")
            )
        )
        val orderService = OrderService(orderRepository)
        `when`(orderRepository.findAll()).thenReturn(expected)

        val listOfOrders = orderService.getAll()

        assertThat(listOfOrders)
            .usingRecursiveComparison()
            .isEqualTo(expected)

        verify(orderRepository, times(1)).findAll()
    }

    @Test
    fun `when order does not exist and get all should return empty`(){
        val orderService = OrderService(orderRepository)
        `when`(orderRepository.findAll()).thenReturn(emptyList())

        val listOfOrders = orderService.getAll()

        assertThat(listOfOrders)
            .isEmpty()

        verify(orderRepository, times(1)).findAll()
    }
}