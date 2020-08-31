package runsystem.vn.kotlin.dto.response

import runsystem.vn.kotlin.dto.response.ResponseBaseDto

data class ResponseExceptionDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp: Long) : ResponseBaseDto(result, code, message, timestamp = 1L) {
    constructor() : this(result = "", code = "", message = "", timestamp = 0L)
}