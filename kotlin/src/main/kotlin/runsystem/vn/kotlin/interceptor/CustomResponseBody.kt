package runsystem.vn.kotlin.interceptor

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import runsystem.vn.kotlin.service.LoggingService

@ControllerAdvice
class CustomResponseBody(val loggingService: LoggingService) : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest,
                                 response: ServerHttpResponse): Any? {
        if (request is ServletServerHttpRequest && response is ServletServerHttpResponse) {
            loggingService.logResponse(request.servletRequest, response.servletResponse, body)
        }
        return body
    }
}