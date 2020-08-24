package runsystem.vn.kotlin

data class ResponseExceptionDto(
        var result: String,
        var code: String,
        var message: String) : ResponseBaseDto(result, code, message) {
}