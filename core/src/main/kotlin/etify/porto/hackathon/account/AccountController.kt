package etify.porto.hackathon.account

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("api/login")
    fun login(@RequestBody command: LoginCommand): Session {
        return accountService.login(command)
    }
}