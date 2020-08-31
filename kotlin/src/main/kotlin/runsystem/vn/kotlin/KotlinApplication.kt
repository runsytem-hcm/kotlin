package runsystem.vn.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import java.time.ZoneId
import java.util.*

@SpringBootApplication
@EnableJpaRepositories
class KotlinApplication

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Ho_Chi_Minh")))
}
