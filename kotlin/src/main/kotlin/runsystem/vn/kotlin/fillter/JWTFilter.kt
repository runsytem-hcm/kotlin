package runsystem.vn.kotlin.fillter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import runsystem.vn.kotlin.common.constants.CommonConstants
import runsystem.vn.kotlin.common.utils.ResponseUtils
import runsystem.vn.kotlin.common.utils.Utils
import runsystem.vn.kotlin.dto.response.ResponseExceptionDto
import runsystem.vn.kotlin.exception.UserCustomException
import runsystem.vn.kotlin.security.JwtToken
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter(private val jwtToken: JwtToken) : OncePerRequestFilter() {

    private val permitsAll: List<String> = listOf("/api/v1/authenticate")
    private val checkAuth: String = "/api/"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val jwt = jwtToken.parseJwt(request)
        if (jwt != null) {
            val tokenDto = jwtToken.verifyToken(jwt)
            if (tokenDto == null) {
                ResponseUtils().customResponseFilter(response)
                return
            } else {
                val authentication = jwtToken.getAuthentication(jwt, tokenDto)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } else if (request.requestURI.startsWith(checkAuth) && !permitsAll.contains(request.requestURI)) {
            ResponseUtils().customResponseFilter(response)
            return
        }
        filterChain.doFilter(request, response);
    }
}