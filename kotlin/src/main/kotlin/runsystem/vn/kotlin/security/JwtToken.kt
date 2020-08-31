package runsystem.vn.kotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import runsystem.vn.kotlin.dto.VerifyTokenDto
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.util.*
import io.jsonwebtoken.security.Keys
import runsystem.vn.kotlin.dto.UserDataDto
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.util.StringUtils
import java.security.Key
import javax.servlet.http.HttpServletRequest


@Component
class JwtToken(
        @Value("\${jwt.secret}")
        private val secret: String
) {
    private var secretKey: ByteArray = secret.toByteArray(StandardCharsets.UTF_8)
    private val logger: Logger = LoggerFactory.getLogger(JwtToken::class.java)
    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())
    fun doGenerateToken(subject: String, user: Any): String {
        return Jwts
                .builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(Date(System.currentTimeMillis() + 84600000))
                .claim("user", user)
                .compact()
    }

    fun getAuthentication(jwtToken: String, tokenDto: VerifyTokenDto): Authentication {
        val authorities = tokenDto.authorities.stream().map(::SimpleGrantedAuthority)
                .collect(Collectors.toList<GrantedAuthority>())

//        val authorities = tokenDto.authorities.stream().map { authority -> SimpleGrantedAuthority(authority) }.collect(Collectors.toList<GrantedAuthority>())

        val principal = User(tokenDto.userName, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, null, authorities)
    }

    fun verifyToken(token: String): VerifyTokenDto? {
        val tokenDto = VerifyTokenDto()
        if (validateToken(token)) {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
            val data = ObjectMapper().readValue<UserDataDto>(ObjectMapper().writeValueAsString(claims["user"]), UserDataDto::class.java)
            tokenDto.userName = claims.subject
            tokenDto.authorities = data.authorities
            return tokenDto
        }
        return null
    }

    private fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT Signature: {}", ex)
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", ex)
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token: {}", ex)
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT exception: {}", ex)
        } catch (ex: IllegalArgumentException) {
            logger.error("Jwt claims string is empty: {}", ex)
        }
        return false
    }

    fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length)
        }
        return null
    }
}