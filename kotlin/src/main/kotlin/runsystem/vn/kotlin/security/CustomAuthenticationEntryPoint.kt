package runsystem.vn.kotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import runsystem.vn.kotlin.dto.response.ResponseExceptionDto
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        if (response != null && authException != null) {
            val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
            response.contentType = "application/json;charset=UTF-8"
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(ResponseExceptionDto(result = "1", code = "2", message = authException.localizedMessage, timestamp = timestamp)))
        }
    }
}