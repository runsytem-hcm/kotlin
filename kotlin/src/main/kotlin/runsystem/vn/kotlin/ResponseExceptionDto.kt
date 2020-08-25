package runsystem.vn.kotlin

data class ResponseExceptionDto(
        var result: String,
        var code: String,
        var message: String,
        var timestamp: Long) : ResponseBaseDto(result, code, message, timestamp = 1L) {
}