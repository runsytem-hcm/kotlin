package runsystem.vn.kotlin

data class FieldErrorsResponseDto(
        var result: String,
        var code: String,
        var message: String,
        var fieldErrors: List<FieldErrors>): ResponseBaseDto(result, code, message) {
}