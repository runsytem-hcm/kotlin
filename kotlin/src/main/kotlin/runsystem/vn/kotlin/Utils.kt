package runsystem.vn.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class Utils {

    public fun getMessage(code: String, language: String, array: kotlin.Array<String>, messageSource: ReloadableResourceBundleMessageSource): String {
        return messageSource.getMessage(code, array, Locale(language))
    }

    public fun setResponseSuccess(obj: Any, data: Any, message: String): Any{
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        val clz = obj.javaClass.declaredFields
        for(f in clz) {
            f.isAccessible = true
            if(f.name == "result"){
                f.set(obj, "OK")
            }
            if(f.name == "code"){
                f.set(obj, "9999")
            }
            if(f.name == "message"){
                f.set(obj, message)
            }
            if(f.name == "timestamp"){
                f.set(obj, timestamp)
            }
            if(f.type == Any::class.java){
                f.set(obj, data)
            }
        }
        return obj
    }
}