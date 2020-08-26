package runsystem.vn.kotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import runsystem.vn.kotlin.interceptor.LogInterceptor
import java.nio.charset.StandardCharsets


@Configuration
class WebConfig(
        private val logInterceptor: LogInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(logInterceptor)
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(0, StringHttpMessageConverter(StandardCharsets.UTF_8))
        return restTemplate
    }
}