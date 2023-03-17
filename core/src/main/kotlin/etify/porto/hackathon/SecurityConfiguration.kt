package etify.porto.hackathon

import etify.porto.hackathon.account.AccountRepository
import etify.porto.hackathon.account.SessionRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        http.csrf { it.disable() }
        http.cors { it.disable() }
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer() = WebSecurityCustomizer {
        it.ignoring()
            .anyRequest()
    }
}

