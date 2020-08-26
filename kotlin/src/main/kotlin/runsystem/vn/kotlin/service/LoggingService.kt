package runsystem.vn.kotlin.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class LoggingService(
       private val LOG: org.slf4j.Logger = LoggerFactory.getLogger(LoggingService::class.java)
) {
    fun logRequest(httpServletRequest: HttpServletRequest, body: Any?) {
        try {
            val stringBuilder = StringBuilder()
            val parameters = buildParametersMap(httpServletRequest)
            stringBuilder.append("REQUEST ")
            stringBuilder.append("method=[").append(httpServletRequest.method).append("] ")
            stringBuilder.append("path=[").append(httpServletRequest.requestURI).append("] ")
            if (parameters != null) {
                if (parameters.isNotEmpty()) {
                    stringBuilder.append("parameters=[").append(parameters).append("] ")
                }
            }
            if (body != null) {
                val mapper = ObjectMapper()
                stringBuilder.append("body=[" + mapper.writeValueAsString(body).toString() + "]")
            }
            LOG.info("{}", stringBuilder)
        } catch (e: Exception) {
            LOG.error(e.message)
        }
    }

    fun logResponse(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse,
                    body: Any?) {
        try {
            val stringBuilder = StringBuilder()
            val mapper = ObjectMapper()
            stringBuilder.append("RESPONSE ")
            stringBuilder.append("method=[").append(httpServletRequest.method).append("] ")
            stringBuilder.append("path=[").append(httpServletRequest.requestURI).append("] ")
            stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ")
            stringBuilder.append("responseBody=[").append(mapper.writeValueAsString(body).toString()).append("] ")
            LOG.info("{}", stringBuilder)
        } catch (e: java.lang.Exception) {
            LOG.error(e.message)
        }
    }

    private fun buildParametersMap(httpServletRequest: HttpServletRequest): Map<String, String>? {
        val resultMap: MutableMap<String, String> = HashMap()
        val parameterNames = httpServletRequest.parameterNames
        while (parameterNames.hasMoreElements()) {
            val key = parameterNames.nextElement()
            val value = httpServletRequest.getParameter(key)
            resultMap[key] = value
        }
        return resultMap
    }

    private fun buildHeadersMap(response: HttpServletResponse): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        val headerNames: Collection<String> = response.headerNames
        for (header in headerNames) {
            map[header] = response.getHeader(header)
        }
        return map
    }
}