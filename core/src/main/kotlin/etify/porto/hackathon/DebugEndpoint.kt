package etify.porto.hackathon

import etify.porto.hackathon.monerium.MoneriumService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugEndpoint(
    private val moneriumService: MoneriumService
) {

    @GetMapping("createMoneriumAccount")
    fun createMoneriumAccount() {
        moneriumService.createAccount()
    }

}