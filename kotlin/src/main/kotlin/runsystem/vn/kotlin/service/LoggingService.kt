package runsystem.vn.kotlin.service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


interface LoggingService {
    fun logRequest(httpServletRequest: HttpServletRequest, body: Any?)
    fun logResponse(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse,
                    body: Any?)
}