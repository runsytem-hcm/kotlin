package runsystem.vn.kotlin

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import java.util.*
import java.util.stream.Collectors

@ControllerAdvice
class ExceptionTranslator(private var messageSource: ReloadableResourceBundleMessageSource): ResponseEntityExceptionHandler(){

    @ExceptionHandler(value = [Exception::class])
    fun handleProcessException(ex: Exception,request: WebRequest): ResponseEntity<ResponseExceptionDto> {
        val lang = request.getHeader("Accept-Language").toString()
        println("${ex.printStackTrace()}")
        val msg = Utils().getMessage("hello", lang, arrayOf("12", "33"), messageSource)
        return ResponseEntity(ResponseExceptionDto(result = "NG", code = "1111", message = msg), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {

        val fieldErrors = ex.bindingResult.fieldErrors.stream()
                .map { f -> FieldErrors(f.field, f.defaultMessage.toString())}
                .collect(Collectors.toList())
        val res = FieldErrorsResponseDto(result = "NG", code = "11111", message = "", fieldErrors = fieldErrors)
        return ResponseEntity(res, status)
    }
}