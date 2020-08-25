package runsystem.vn.kotlin

data class FieldErrorsResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp : Long,
        var fieldErrors: List<FieldErrors>) : ResponseBaseDto(result, code, message, timestamp = 1L) {
}