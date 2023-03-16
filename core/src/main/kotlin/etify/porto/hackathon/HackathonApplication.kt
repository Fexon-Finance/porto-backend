package etify.porto.hackathon

import feign.Feign
import feign.Logger
import okhttp3.OkHttpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableFeignClients
class HackathonApplication

fun main(args: Array<String>) {
    runApplication<HackathonApplication>(*args)
}

@Configuration
class FeignConfig {

    fun b() {
        Feign.builder()
    }

    @Bean
    fun client(): OkHttpClient {
        return OkHttpClient()
    }
}