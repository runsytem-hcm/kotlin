package runsystem.vn.kotlin.dto.response

open class ResponseBaseDto(
        private val result: String,
        private val code: String,
        private val message: String,
        private val timestamp: Long) {
}