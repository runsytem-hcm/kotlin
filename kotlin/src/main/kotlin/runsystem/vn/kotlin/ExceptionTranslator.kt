package runsystem.vn.kotlin

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.stream.Collectors

@ControllerAdvice
class ExceptionTranslator(private var messageSource: ReloadableResourceBundleMessageSource) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Exception::class])
    fun handleProcessException(
            ex: Exception,
            request: WebRequest): ResponseEntity<ResponseExceptionDto> {
        val lang = request.getHeader("Accept-Language").toString()
        println("${ex.printStackTrace()}")
        val msg = Utils().getMessage("hello", lang, arrayOf("12", "33"), messageSource)
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        return ResponseEntity(ResponseExceptionDto(result = "NG", code = "1111", message = msg, timestamp = timestamp), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // Validation param
    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {

        val fieldErrors = ex.bindingResult.fieldErrors.stream()
                .map { f -> FieldErrors(f.field, f.defaultMessage.toString()) }
                .collect(Collectors.toList())
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        return ResponseEntity(FieldErrorsResponseDto(result = "NG", code = "11111", message = "", fieldErrors = fieldErrors, timestamp = timestamp), status)
    }

    // Exception Header validation
    override fun handleServletRequestBindingException(
            ex: ServletRequestBindingException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        return ResponseEntity(ex.message?.let { ResponseExceptionDto(result = "NG", code = "1111", message = it, timestamp = timestamp) }, status)
    }

    // Exception Path Not Found
    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        return ResponseEntity(ex.message?.let { ResponseExceptionDto(result = "NG", code = "1111", message = it, timestamp = timestamp) }, status)
    }

    // Exception URL Not Found
    override fun handleExceptionInternal(
            ex: Exception,
            body: Any?,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()).time
        return ResponseEntity(ex.message?.let { ResponseExceptionDto(result = "NG", code = "1111", message = it, timestamp = timestamp) }, status)
    }
}