package br.com.samuellfa.cashback.application.security

import br.com.samuellfa.cashback.domain.reseller.Reseller
import br.com.samuellfa.cashback.domain.reseller.ResellerRepository
import br.com.samuellfa.cashback.domain.token.TokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationByTokenFilter(
    private val tokenService: TokenService,
    private val resellerRepository: ResellerRepository
) : OncePerRequestFilter() {

    private fun authenticateUser(token: String) {
        val userId: Long = tokenService.getUserId(token)
        val reseller: Reseller = resellerRepository.findById(userId).get()
        val authentication = UsernamePasswordAuthenticationToken(reseller, null, reseller.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        return if (token == null || !token.startsWith("Bearer ")) {
            null
        } else token.substring(7, token.length)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        getToken(request)
            .let { token ->
                val valid = tokenService.isValidToken(token)
                if (valid) {
                    authenticateUser(token!!)
                }
                filterChain.doFilter(request, response)
            }
    }
}
