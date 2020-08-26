package runsystem.vn.kotlin.interceptor

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

import runsystem.vn.kotlin.service.LoggingService
import javax.servlet.DispatcherType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogInterceptor(
        private val loggingService: LoggingService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (DispatcherType.REQUEST.name == request.dispatcherType.name && request.method == HttpMethod.GET.name) {
            loggingService.logRequest(request, null);
        }
        return true;
    }
}