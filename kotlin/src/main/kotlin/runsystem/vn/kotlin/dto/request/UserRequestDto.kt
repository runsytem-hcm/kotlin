package runsystem.vn.kotlin.dto.request

import runsystem.vn.kotlin.validator.annotation.age.MinimumAge
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRequestDto(

        @field: NotBlank(message = "{hello}")
        private val name: String,

        @field: NotBlank(message = "{hello}")
        private val address: String,

        @field: NotNull(message = "{hello}")
        @MinimumAge(message = "{age}", age1 = 11)
        private val age: Int?) {
}