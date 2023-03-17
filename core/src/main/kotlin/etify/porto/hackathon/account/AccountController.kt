package etify.porto.hackathon.account

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("api/accounts")
    fun createAccount(@RequestBody command: CreateAccountCommand) {
        accountService.createAccount(command)
    }

    @PostMapping("api/login")
    fun login(@RequestBody command: LoginCommand): Session {
        return accountService.login(command)
    }
}