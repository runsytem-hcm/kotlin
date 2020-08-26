package runsystem.vn.kotlin

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1")
class UserController(
        private val userService: UserService,
        private val messageSource: ReloadableResourceBundleMessageSource,
        private val jwtToken: JwtToken
) {
    @GetMapping("/list")
    fun getAll(): ResponseEntity<Any> {
        println(jwtToken.doGenerateToken("Hiep"))
        val res = userService.getAllUser()
        val msg = Utils().getMessage("hello", "ja", arrayOf("12", "33"), messageSource)
        //UserResponseDto(result = "OK", code = "9999", message = msg, user = res)
        return ResponseEntity(Utils().setResponseSuccess(UserResponseDto(), res, msg), HttpStatus.OK)
    }

    @GetMapping("/name")
    fun getList(@RequestParam("name") name: String, @RequestHeader("language") lang: String): ResponseEntity<Any> {
        val res = userService.getList(name)
        val msg = Utils().getMessage("hello", lang, arrayOf("12", "33"), messageSource)
        return ResponseEntity(Utils().setResponseSuccess(UserResponseDto(), res, msg), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long, @RequestHeader("Accept-Language", required = true) lang: String): ResponseEntity<Any> {
        val res = userService.getUser(id)
        val msg = messageSource.getMessage("hello", arrayOf("aaa", "babb"), Locale(lang))
        if (res != null) {
            return ResponseEntity(Utils().setResponseSuccess(UserResponseDto(), res, msg), HttpStatus.OK)
        }
        return ResponseEntity(Utils().setResponseSuccess(UserResponseDto(), "", msg), HttpStatus.NOT_FOUND)
    }

    @PostMapping("/add")
    fun addUser(@Valid @RequestBody user: UserRequestDto, @RequestHeader("Accept-Language") lang: String): ResponseEntity<Any> {
        val msg = messageSource.getMessage("hello", arrayOf("aaa", "babb"), Locale(lang))
        userService.addUser(user)
        return ResponseEntity(Utils().setResponseSuccess(UserResponseDto(), "", msg), HttpStatus.OK)
    }
}