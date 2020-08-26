package runsystem.vn.kotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import runsystem.vn.kotlin.security.CustomAuthenticationEntryPoint


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(
//        private val filter: JWTFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
//                .and()
//                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }
}