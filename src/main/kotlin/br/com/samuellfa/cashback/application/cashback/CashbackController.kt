package br.com.samuellfa.cashback.application.cashback

import org.hibernate.validator.constraints.br.CPF
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@Validated
class CashbackController(
    val restTemplate: RestTemplate
) {

    @GetMapping("/cashback/{document}")
    fun getTotalCashbackByDocument(@CPF @PathVariable document: String): ResponseEntity<String> {

        return restTemplate.getForEntity("https://mdaqk8ek5j.execute-api.us-east-1.amazonaws.com/v1/cashback?cpf=${document}", String::class.java)
    }
}