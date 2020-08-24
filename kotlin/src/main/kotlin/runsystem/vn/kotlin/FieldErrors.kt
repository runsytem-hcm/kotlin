package runsystem.vn.kotlin

data class FieldErrors(val field: String, val message: String) {
    constructor(): this(field="", message = "")
}