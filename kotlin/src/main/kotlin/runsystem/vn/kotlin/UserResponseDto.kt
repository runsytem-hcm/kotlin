package runsystem.vn.kotlin

import com.fasterxml.jackson.annotation.JsonInclude


data class UserResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var user: Any): ResponseBaseDto(result, code, message)
{
    constructor(): this(result = "", code = "", message = "", user = "")
}
