package runsystem.vn.kotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import runsystem.vn.kotlin.dto.VerifyTokenDto
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.SignatureException
import java.util.*
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import runsystem.vn.kotlin.dto.UserDataDto
import java.util.stream.Collectors
import kotlin.collections.LinkedHashMap
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.terracotta.management.model.stats.StatisticRegistry.collect
import org.springframework.security.core.GrantedAuthority


@Component
class JwtToken(
        @Value("\${jwt.secret}")
        private val secret: String
) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(JwtToken::class.java)
    }


//    init {
//        val byte = secret.toByteArray(StandardCharsets.UTF_8)
//        this.key = Keys.hmacShaKeyFor(byte)
//    }

    fun doGenerateToken(subject: String, user: Any): String {
        return Jwts
                .builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret.toByteArray(StandardCharsets.UTF_8))
                .setExpiration(Date(System.currentTimeMillis() + 86400000))
                .claim("user", user)
                .compact()
    }

    fun getAuthentication(tokenDto: VerifyTokenDto): Authentication{
        val authorities = tokenDto.authorities.stream().map(::SimpleGrantedAuthority).collect(Collectors.toList<GrantedAuthority>())

        val principal = User(tokenDto.userName, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, tokenDto, authorities)
    }

    fun verifyToken(token: String): VerifyTokenDto {
        val tokenDto = VerifyTokenDto()
        if(validateToken(token)){
            val claims = Jwts.parser().setSigningKey(secret.toByteArray(StandardCharsets.UTF_8)).parseClaimsJws(token).body
            tokenDto.userName = claims.subject
            val user = claims["user"]
            val mapper = ObjectMapper()
            val data = mapper.readValue<UserDataDto>(ObjectMapper().writeValueAsString(user), UserDataDto::class.java)
            val list: List<String> = listOf()
            tokenDto.authorities = list.plus(data.address)
        }

        return tokenDto
    }


    private fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret.toByteArray(StandardCharsets.UTF_8)).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            LOG.error("Invalid JWT Signature: {}", ex)
        } catch (ex: MalformedJwtException) {
            LOG.error("Invalid JWT token: {}", ex)
        } catch (ex: ExpiredJwtException) {
            LOG.error("Expired JWT token: {}", ex)
        } catch (ex: UnsupportedJwtException) {
            LOG.error("Unsupported JWT exception: {}", ex)
        } catch (ex: IllegalArgumentException) {
            LOG.error("Jwt claims string is empty: {}", ex)
        }
        return false
    }
}