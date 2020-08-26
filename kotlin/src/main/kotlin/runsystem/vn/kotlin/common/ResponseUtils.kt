package runsystem.vn.kotlin.common

import runsystem.vn.kotlin.dto.FieldErrors
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime

class ResponseUtils {

    public fun createResponseSuccess(obj: Any, data: Any, message: String): Any{
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(Constants().CONST_TIME_ZONE)).toLocalDateTime()).time
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

    public fun createResponseError(obj: Any, message: String, fieldErrors: List<FieldErrors>?): Any{
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of(Constants().CONST_TIME_ZONE)).toLocalDateTime()).time
        val clz = obj.javaClass.declaredFields
        for(f in clz) {
            f.isAccessible = true
            if(f.name == "result"){
                f.set(obj, "NG")
            }
            if(f.name == "code"){
                f.set(obj, "1111")
            }
            if(f.name == "message"){
                f.set(obj, message)
            }
            if(f.name == "timestamp"){
                f.set(obj, timestamp)
            }
            if(f.name == "fieldErrors"){
                f.set(obj, fieldErrors)
            }
        }
        return obj
    }
}