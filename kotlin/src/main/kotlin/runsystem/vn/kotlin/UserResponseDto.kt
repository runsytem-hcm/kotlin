package runsystem.vn.kotlin

import com.fasterxml.jackson.annotation.JsonInclude


data class UserResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp: Long,
        var user: Any): ResponseBaseDto(result, code, message, timestamp = 1L)
{
    constructor(): this(result = "", code = "", message = "", timestamp = 1L, user = "")
}
