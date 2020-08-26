package runsystem.vn.kotlin.dto

data class FieldErrors(val field: String, val message: String) {
    constructor(): this(field="", message = "")
}