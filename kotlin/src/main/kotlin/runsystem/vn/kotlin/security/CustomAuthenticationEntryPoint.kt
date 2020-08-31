package runsystem.vn.kotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import runsystem.vn.kotlin.common.constants.CommonConstants
import runsystem.vn.kotlin.dto.response.ResponseExceptionDto
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?,
                        accessDeniedException: AccessDeniedException?) {
        if (response != null && accessDeniedException != null) {
            val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(CommonConstants().CONST_TIME_ZONE)).toLocalDateTime()).time
            response.contentType = "application/json;charset=UTF-8"
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write(ObjectMapper().writeValueAsString(ResponseExceptionDto(result = "1", code = "2", message = accessDeniedException.localizedMessage, timestamp = timestamp)))
        }
    }
}