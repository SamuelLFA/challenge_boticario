package br.com.samuellfa.cashback.domain.authentication

import br.com.samuellfa.cashback.domain.reseller.ResellerService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    val resellerService: ResellerService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        resellerService.getResellerByEmail(username)
            ?: throw UsernameNotFoundException("Data invalid")
}