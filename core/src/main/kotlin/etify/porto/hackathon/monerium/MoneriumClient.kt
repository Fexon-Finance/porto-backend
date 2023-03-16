package etify.porto.hackathon.monerium

import etify.porto.hackathon.FeignConfig
import etify.porto.hackathon.monerium.data.*
import feign.Feign
import feign.Logger
import feign.slf4j.Slf4jLogger
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    value = "moneriumClient",
    url = "https://sandbox.monerium.dev/api",
    configuration = [FeignConfig::class]
)
interface MoneriumClient {
    @PostMapping("emoney/profiles/{profileId}/addresses")
    fun addAccountAddress(
        @RequestHeader("Authorization") authorizationHeader: String,
        @PathVariable profileId: String,
        @RequestBody body: AddAccountAddressData
    ): AddAccountAddressResponse

    @GetMapping("emoney/profiles/{profileId}/accounts")
    fun getAccounts(
        @RequestHeader("Authorization") authorizationHeader: String,
        @PathVariable profileId: String,
    ): List<MoneriumAccount>

    @PatchMapping("emoney/accounts/{accountId}")
    fun patchAccountData(
        @RequestHeader("Authorization") authorizationHeader: String,
        @PathVariable accountId: String,
        @RequestBody body: PatchAccountData
    ): PatchAccountResponse

    @PatchMapping("treasury/accounts/{treasureId}")
    fun patchTreasuryData(
        @RequestHeader("Authorization") authorizationHeader: String,
        @PathVariable treasureId: String,
        @RequestBody body: PatchTreasuryData
    )
}

@Configuration
class ClientConfiguration(
) {

    @Bean
    fun moneriumClient(): Feign.Builder = Feign.builder()
        .client(feign.okhttp.OkHttpClient())
        .logger(Slf4jLogger())
        .logLevel(Logger.Level.FULL)

//        .build()
}



