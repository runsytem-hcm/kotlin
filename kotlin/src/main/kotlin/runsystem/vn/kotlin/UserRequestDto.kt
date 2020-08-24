package runsystem.vn.kotlin

import javax.validation.constraints.NotEmpty

data class UserRequestDto(
        @field:NotEmpty(message = "{hello}")
        val name: String,
        val address: String,
        @MinimumAge(message = "{age}", age1 = 11)
        val age: Int) {
}