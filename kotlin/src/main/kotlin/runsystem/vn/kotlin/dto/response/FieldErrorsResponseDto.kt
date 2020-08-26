package runsystem.vn.kotlin.dto.response

import runsystem.vn.kotlin.dto.FieldErrors

data class FieldErrorsResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp : Long,
        var fieldErrors: List<FieldErrors>) : ResponseBaseDto(result, code, message, timestamp = 1L) {
    constructor(): this(result = "", code = "", message = "", timestamp = 0L, fieldErrors = listOf())
}