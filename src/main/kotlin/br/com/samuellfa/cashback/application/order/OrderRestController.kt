package br.com.samuellfa.cashback.application.order

import br.com.samuellfa.cashback.domain.order.OrderService
import br.com.samuellfa.cashback.domain.reseller.ResellerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
class OrderRestController(
    val resellerService: ResellerService,
    val orderService: OrderService
) {

    @PostMapping("/orders")
    fun create(
        @RequestBody @Valid form: OrderPostRestRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<OrderResponseDTO> {

        val entityToSave = form.toEntity(resellerService)
        val orderSaved = orderService.saveOrder(entityToSave)
        val uri = uriBuilder.path("/resellers/{id}").buildAndExpand(orderSaved.id).toUri()
        val dto = OrderResponseDTO(orderSaved)
        return ResponseEntity.created(uri).body(dto)
    }

    @GetMapping("/orders")
    fun getOrder(): ResponseEntity<List<OrderResponseDTO>> =
        ResponseEntity.ok(
            orderService
                .getAll()
                .map {
                    OrderResponseDTO(it)
                }
        )
}