package br.com.samuellfa.cashback.domain.reseller

import javax.persistence.*

@Entity
@Table(name = "resellers")
data class Reseller(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val document: String,
    val email: String,
    val password: String
)