package runsystem.vn.kotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import runsystem.vn.kotlin.fillter.JWTFilter
import runsystem.vn.kotlin.security.CustomAuthenticationEntryPoint


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(
        private val filter: JWTFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling().accessDeniedHandler(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/list").hasAuthority("list")
    }

    @Bean
    fun authenticationEntryPoint(): AccessDeniedHandler {
        return CustomAuthenticationEntryPoint()
    }
}