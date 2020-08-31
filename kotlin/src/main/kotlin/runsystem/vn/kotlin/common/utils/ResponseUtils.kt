package runsystem.vn.kotlin.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import runsystem.vn.kotlin.common.constants.CommonConstants
import runsystem.vn.kotlin.dto.FieldErrors
import runsystem.vn.kotlin.dto.response.ResponseExceptionDto
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletResponse

/**
 * Response utils
 *
 * @constructor Create empty Response utils
 */
class ResponseUtils {

    /**
     * Create response success
     *
     * @param obj
     * @param data
     * @param message
     * @return
     */
    public fun createResponseSuccess(obj: Any, data: Any, message: String): Any {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(CommonConstants().CONST_TIME_ZONE)).toLocalDateTime())
                .time
        val clz = obj.javaClass.declaredFields
        for (f in clz) {
            f.isAccessible = true
            when (f.name) {
                "result" -> f.set(obj, "OK")
                "code" -> f.set(obj, "9999")
                "message" -> f.set(obj, message)
                "timestamp" -> f.set(obj, timestamp)
                else -> f.set(obj, data)
            }

        }
        return obj
    }

    /**
     * Create response error
     *
     * @param obj
     * @param message
     * @param errorCode
     * @param fieldErrors
     * @return
     */
    public fun createResponseError(obj: Any, message: String, errorCode: String, fieldErrors: List<FieldErrors>?): Any {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(CommonConstants().CONST_TIME_ZONE)).toLocalDateTime())
                .time
        val clz = obj.javaClass.declaredFields
        for (f in clz) {
            f.isAccessible = true
            when (f.name) {
                "result" -> f.set(obj, "NG")
                "code" -> f.set(obj, errorCode)
                "message" -> f.set(obj, message)
                "timestamp" -> f.set(obj, timestamp)
                "fieldErrors" -> f.set(obj, fieldErrors)
            }
        }
        return obj
    }

    public fun customResponseFilter(res: HttpServletResponse) {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(CommonConstants().CONST_TIME_ZONE)).toLocalDateTime())
                .time
        res.contentType = "application/json;charset=UTF-8"
        res.status = HttpStatus.UNAUTHORIZED.value()
        res.writer.write(ObjectMapper().writeValueAsString(ResponseExceptionDto(result = "1", code = "2", message = "Access is denied", timestamp = timestamp)))
    }
}