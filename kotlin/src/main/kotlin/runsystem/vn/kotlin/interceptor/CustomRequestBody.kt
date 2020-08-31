package runsystem.vn.kotlin.interceptor

import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import runsystem.vn.kotlin.service.LoggingService
import java.lang.reflect.Type
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class CustomRequestBody(
        private val httpServletRequest: HttpServletRequest,
        private val loggingService: LoggingService) : RequestBodyAdviceAdapter() {

    override fun supports(methodParameter: MethodParameter, targetType: Type,
                          converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun afterBodyRead(body: Any, inputMessage: HttpInputMessage, parameter: MethodParameter, targetType: Type,
                               converterType: Class<out HttpMessageConverter<*>>): Any {
        loggingService.logRequest(httpServletRequest, body)
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType)
    }
}