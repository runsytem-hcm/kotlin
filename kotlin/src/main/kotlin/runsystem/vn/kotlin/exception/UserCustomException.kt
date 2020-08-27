package runsystem.vn.kotlin.exception

import org.springframework.http.HttpStatus
import java.lang.Exception


data class UserCustomException(override val message: String, val errorCode: String, val status: HttpStatus): Exception(message){
}