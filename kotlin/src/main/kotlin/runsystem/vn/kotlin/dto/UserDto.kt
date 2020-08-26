package runsystem.vn.kotlin.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class UserDto(
        var id: Long,
        var name: String,
        var address: String,

        @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        var date: LocalDateTime?) {
    constructor() : this(id = 0L, name = "", address = "", date = null)
}