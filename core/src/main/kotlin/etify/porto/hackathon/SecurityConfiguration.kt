package etify.porto.hackathon

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer

@Configuration
class SecurityConfiguration {

    @Bean
    fun webSecurityCustomizer() = WebSecurityCustomizer {
        it.ignoring()
            .anyRequest()
    }
}