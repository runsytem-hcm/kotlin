package runsystem.vn.kotlin.common.utils

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import runsystem.vn.kotlin.common.constants.CommonConstants
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Utils
 *
 * @constructor Create empty Utils
 */
class Utils {
    /**
     * Get message
     *
     * @param code
     * @param language
     * @param array
     * @param messageSource
     * @return
     */
    public fun getMessage(code: String, language: String, array: kotlin.Array<String>, messageSource: ReloadableResourceBundleMessageSource): String {
        return messageSource.getMessage(code, array, Locale(language))
    }

    /**
     * Get current date time
     *
     * @return
     */
    public fun getCurrentDateTime(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.of(CommonConstants().CONST_TIME_ZONE))
    }

    /**
     * Convert date time to string
     *
     * @param dateTime
     * @param pattern
     * @return
     */
    public fun convertDateTimeToString(dateTime: ZonedDateTime, pattern: String): String {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
    }
}