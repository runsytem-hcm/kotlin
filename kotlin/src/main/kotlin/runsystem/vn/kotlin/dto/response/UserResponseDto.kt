package runsystem.vn.kotlin.dto.response

import runsystem.vn.kotlin.dto.response.ResponseBaseDto


data class UserResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp: Long,
        var user: Any): ResponseBaseDto(result, code, message, timestamp = 1L)
{
    constructor(): this(result = "", code = "", message = "", timestamp = 1L, user = "")
}
