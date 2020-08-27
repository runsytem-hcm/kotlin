package runsystem.vn.kotlin.dto

data class VerifyTokenDto(
        var userName: String,
        var authorities: List<String>) {
    constructor(): this(userName = "", authorities = listOf())
}