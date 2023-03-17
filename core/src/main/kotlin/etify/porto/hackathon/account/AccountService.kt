package etify.porto.hackathon.account

import etify.porto.hackathon.dfns.DfnsService
import etify.porto.hackathon.dfns.data.AssetAccount
import etify.porto.hackathon.monerium.MoneriumService
import etify.porto.hackathon.monerium.data.MoneriumAccount
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

interface AccountService {
    fun login(command: LoginCommand): SessionDto
}

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val dfnsService: DfnsService,
    private val moneriumService: MoneriumService,
    private val passwordEncoder: PasswordEncoder,
    private val sessionRepository: SessionRepository
) : AccountService {
    companion object {
        private val MESSAGE = "I hereby declare that I am the address owner.".encodeToByteArray()
    }

    private fun createAccount(vaultId: String): Account {
//        Avoiding to create  accounts on custodian and monerium when integrating with frontend
//        val assetAccount = dfnsService.createAssetAccount()
//
//        val signedMessage = dfnsService.sign(
//            publicKeyId = assetAccount.publicKey ?: throw IllegalStateException("Public key has not been created."),
//            messageBytes = MESSAGE
//        )
//
//        val moneriumAccount = moneriumService.createAccount(
//            accountAddress = assetAccount.address ?: throw IllegalStateException("Address has not been created."),
//            signedBytes = signedMessage
//        )

        val assetAccount = AssetAccount(
            id = "aa-londo-vermo-7cuuloobe59uaqsk",
            status = "Enabled",
            address = "0xE8E19c5382bd5B0D4dD7F77143CD32999B18bF14",
            publicKey = "pk-april-arizo-2g5uqmhhsf8r1aa8"
        )

        val moneriumAccount = MoneriumAccount(
            id = "12345", address = assetAccount.address!!, iban = "IS13 2635 6907 1360 2643 7306 84"
        )

        val account = Account(
            id = UUID.randomUUID(),
            vaultId = vaultId,
            iban = moneriumAccount.iban,
            publicKey = assetAccount.publicKey ?: throw IllegalStateException("Public key has not been created."),
            walletAddress = assetAccount.address ?: throw IllegalStateException("Address has not been created.")
        )

        return accountRepository.save(account)
    }

    override fun login(command: LoginCommand): SessionDto {
        val account = accountRepository.findByVaultId(command.vaultId).orElseGet { createAccount(command.vaultId) }

        val session = Session(
            id = UUID.randomUUID(),
            account = account.id,
            token = UUID.randomUUID(),
            expiry = OffsetDateTime.now().plusDays(1)
        )
        sessionRepository.save(session)

        return SessionDto(
            id = session.id,
            account = account.id,
            token = session.token,
            expiry = session.expiry,
            iban = account.iban
        )
    }
}

data class SessionDto(
    val id: UUID,
    val account: UUID,
    val iban: String,
    val token: UUID,
    val expiry: OffsetDateTime
)