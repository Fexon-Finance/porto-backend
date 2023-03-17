package etify.porto.hackathon.transaction

import etify.porto.hackathon.account.AccountRepository
import etify.porto.hackathon.project.ProjectRepository
import etify.porto.hackathon.project.Token
import etify.porto.hackathon.web3.Web3
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.web3j.utils.Convert
import java.time.OffsetDateTime
import java.util.UUID

interface TransactionService {

    fun getTransactions(userId: UUID): List<TransactionDto>
    fun initTransaction(command: CreateTransactionCommand, userId: UUID): TransactionDto
    fun getBalance(userId: UUID): List<TokenBalanceDto>
}

@Service
class TransactionServiceImpl(
    val transactionRepository: TransactionRepository,
    val accountRepository: AccountRepository,
    val projectRepository: ProjectRepository,
    val web3: Web3
) : TransactionService {
    override fun getTransactions(userId: UUID): List<TransactionDto> {
        return transactionRepository.findAll().filter { it.accountId == userId }.map { it.toDto() }
    }

    override fun initTransaction(command: CreateTransactionCommand, userId: UUID): TransactionDto {
        val project = projectRepository.findByIdOrNull(command.projectId) ?: throw NoSuchElementException()
        val token = project.tokens.firstOrNull() { it.id == command.tokenId } ?: throw NoSuchElementException()
        val newTransaction =
            TransactionDto(
                id = UUID.randomUUID(),
                price = 12.2,//TODO(getPrice)
                date = OffsetDateTime.now(),
                tokenAmount = command.tokenAmount,
                tokenId = command.tokenId,
                accountId = userId,
                symbol = token.symbol,
                logo = token.logo,
                projectName = project.name
            )
        transactionRepository.save(newTransaction.toDomain())
        return newTransaction
    }

    override fun getBalance(userId: UUID): List<TokenBalanceDto> {
        val account = accountRepository.findById(userId)
            .orElseThrow { throw IllegalStateException("Account doesn't exists") }
        val tokenList = projectRepository.findAll().map { it.tokens }.flatten()
        return transactionRepository.findAll().filter { it.accountId == userId }
            .map { it.tokenId }
            .distinct()
            .mapNotNull { tokenId -> tokenList.find { token -> token.id == tokenId } }
            .map { it.toBalanceDto(account.walletAddress) }
    }

    private fun Token.toBalanceDto(wallet: String): TokenBalanceDto {
        val weiBalance = web3.getBalance(tokenAddress, wallet)
        return TokenBalanceDto(
            id = id,
            name = name,
            symbol = symbol,
            tokenAmount = Convert.fromWei(weiBalance.toString(), Convert.Unit.ETHER).toDouble(),
            price = 0.0,
            logo = logo
        )
    }

    private fun TransactionDto.toDomain(): Transaction {
        return Transaction(
            id = id,
            price = price,
            date = date,
            tokenAmount = tokenAmount,
            tokenId = tokenId,
            accountId = accountId,
            symbol = symbol,
            logo = logo,
            projectName = projectName
        )
    }

    private fun Transaction.toDto(): TransactionDto {
        return TransactionDto(
            id = id,
            price = price,
            date = date,
            tokenAmount = tokenAmount,
            tokenId = tokenId,
            accountId = accountId,
            symbol = symbol,
            logo = logo,
            projectName = projectName
        )
    }
}