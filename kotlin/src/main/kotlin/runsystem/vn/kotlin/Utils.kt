package runsystem.vn.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*


public class Utils {

    public fun getMessage(code: String, language: String, array: kotlin.Array<String>, messageSource: ReloadableResourceBundleMessageSource): String {
        return messageSource.getMessage(code, array, Locale(language))
    }

    public fun setResponseSuccess(obj: Any, data: Any, message: String): Any{
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
            if(f.type == Any::class.java){
                f.set(obj, data)
            }
        }
        return obj
    }
}