package br.com.samuellfa.cashback.domain.token

import br.com.samuellfa.cashback.domain.reseller.Reseller
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${TOKEN_EXPIRATION}")
    lateinit var expiration: String

    @Value("\${TOKEN_SECRET}")
    lateinit var secret: String

    fun createToken(authentication: Authentication): String {

        val user = authentication.principal as Reseller
        val date = Date()
        val expirationDate = Date(date.time + expiration.toLong())
        return Jwts.builder()
            .setIssuer("Cashback")
            .setSubject(user.id.toString())
            .setIssuedAt(date)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun isValidToken(token: String?): Boolean {

        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(token: String?): Long {

        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return claims.subject.toLong()
    }
}
