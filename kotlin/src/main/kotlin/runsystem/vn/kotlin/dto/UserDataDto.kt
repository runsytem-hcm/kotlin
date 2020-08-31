package runsystem.vn.kotlin.dto

data class UserDataDto(
        var address: String,
        var userName: String,
        var phone: String,
        var authorities: List<String>) {
    constructor() : this(address = "", userName = "", phone = "", authorities = listOf())
}