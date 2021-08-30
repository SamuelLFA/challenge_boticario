package br.com.samuellfa.cashback.domain.order

import org.springframework.stereotype.Service

@Service
class OrderService (
    val orderRepository: OrderRepository
) {
    val DOCUMENTS = listOf("15350946056")

    fun getAll(): List<Order> = orderRepository.findAll()

    fun saveOrder(order: Order): Order {
        val orderToSave = exceptionCase(order)
        return orderRepository.save(orderToSave)
    }

    private fun exceptionCase(order: Order): Order =
        order.copy(status = Status.APPROVED)
            .takeIf { DOCUMENTS.contains(order.reseller?.document) }
            ?: order
}