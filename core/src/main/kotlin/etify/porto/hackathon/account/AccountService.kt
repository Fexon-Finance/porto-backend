package etify.porto.hackathon.account

import org.springframework.stereotype.Service

interface AccountService {
    fun createAccount(command: CreateAccountCommand)
}

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository
) : AccountService {
    override fun createAccount(command: CreateAccountCommand) {
        TODO("Not yet implemented")
    }
}