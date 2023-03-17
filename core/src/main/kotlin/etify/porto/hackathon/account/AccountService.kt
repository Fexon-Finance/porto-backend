package etify.porto.hackathon.account

import etify.porto.hackathon.dfns.DfnsService
import etify.porto.hackathon.monerium.MoneriumService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

interface AccountService {
    fun createAccount(command: CreateAccountCommand): Account
}

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val dfnsService: DfnsService,
    private val moneriumService: MoneriumService,
    private val passwordEncoder: PasswordEncoder
) : AccountService {
    companion object {
        private val MESSAGE = "I hereby declare that I am the address owner.".encodeToByteArray()
    }

    override fun createAccount(command: CreateAccountCommand): Account {
        val assetAccount = dfnsService.createAssetAccount()

        val signedMessage = dfnsService.sign(
            publicKeyId = assetAccount.publicKey ?: throw IllegalStateException("Public key has not been created."),
            messageBytes = MESSAGE
        )

        val moneriumAccount = moneriumService.createAccount(
            accountAddress = assetAccount.address ?: throw IllegalStateException("Address has not been created."),
            signedBytes = signedMessage
        )

        val account = Account(
            id = UUID.randomUUID(),
            email = command.email,
            name = command.name,
            surname = command.surname,
            password = passwordEncoder.encode(command.password),
            birthday = command.birthday,
            iban = moneriumAccount.iban,
            publicKey = assetAccount.publicKey ?: throw IllegalStateException("Public key has not been created."),
            walletAddress = assetAccount.address ?: throw IllegalStateException("Address has not been created.")
        )

        return accountRepository.save(account)
    }
}