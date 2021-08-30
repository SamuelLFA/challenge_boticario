package br.com.samuellfa.cashback.application.reseller

import br.com.samuellfa.cashback.domain.reseller.ResellerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
class ResellerRestController(
    val resellerService: ResellerService
) {

    @PostMapping("/resellers")
    fun postUser(
        @Valid @RequestBody form: ResellerPostRequestDTO,
        uriBuilder: UriComponentsBuilder
    ) : ResponseEntity<ResellerResponseDTO> {

        val model = form.toEntity()
        resellerService.saveReseller(model)

        val uri = uriBuilder.path("/resellers/{id}").buildAndExpand(model.id).toUri()
        val reseller = ResellerResponseDTO(model)
        return ResponseEntity.created(uri).body(reseller)
    }
}