package runsystem.vn.kotlin.exception

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import runsystem.vn.kotlin.common.ResponseUtils
import runsystem.vn.kotlin.dto.FieldErrors
import runsystem.vn.kotlin.dto.response.FieldErrorsResponseDto
import runsystem.vn.kotlin.dto.response.ResponseExceptionDto
import runsystem.vn.kotlin.common.Utils
import java.lang.Exception
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.stream.Collectors

@ControllerAdvice
class ExceptionTranslator(private var messageSource: ReloadableResourceBundleMessageSource) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Exception::class])
    fun handleProcessException(
            ex: Exception,
            request: WebRequest): ResponseEntity<Any> {
        val lang = request.getHeader("Accept-Language").toString()
        println("${ex.printStackTrace()}")
        val msg = Utils().getMessage("hello", lang, arrayOf("12", "33"), messageSource)
        return ResponseEntity(ResponseUtils().createResponseError(ResponseExceptionDto(), msg, null), HttpStatus.INTERNAL_SERVER_ERROR)
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
        return ResponseEntity(ResponseUtils().createResponseError(FieldErrorsResponseDto(), "", fieldErrors), status)
    }

    // Exception Header validation
    override fun handleServletRequestBindingException(
            ex: ServletRequestBindingException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ex.message?.let { ResponseUtils().createResponseError(ResponseExceptionDto(), it, null) }, status)
    }

    // Exception Path Not Found
    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ex.message?.let { ResponseUtils().createResponseError(ResponseExceptionDto(), it, null) }, status)
    }

    // Exception URL Not Found
    override fun handleExceptionInternal(
            ex: Exception,
            body: Any?,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ex.message?.let { ResponseUtils().createResponseError(ResponseExceptionDto(), it, null) }, status)
    }
}