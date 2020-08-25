package runsystem.vn.kotlin

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtToken(
        @Value("\${jwt.secret}")
        private val secret: String
) {
    fun doGenerateToken(subject: String): String {
        return Jwts
                .builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(Date(System.currentTimeMillis()))
                .claim("role", UserDataDto(address = "dsds", userName = "dsds", phone = "3333"))
                .compact()
    }
}