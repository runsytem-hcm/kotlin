package runsystem.vn.kotlin.common

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class Utils {

    public fun getMessage(code: String, language: String, array: kotlin.Array<String>, messageSource: ReloadableResourceBundleMessageSource): String {
        return messageSource.getMessage(code, array, Locale(language))
    }
}