package runsystem.vn.kotlin

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.lang.Exception
import java.util.*
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