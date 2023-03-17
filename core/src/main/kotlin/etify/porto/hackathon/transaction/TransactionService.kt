package etify.porto.hackathon.transaction

import etify.porto.hackathon.project.ProjectRepository
import etify.porto.hackathon.project.TokenDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

interface TransactionService {

    fun getTransactions(userId: UUID): List<TransactionDto>
    fun initTransaction(command: CreateTransactionCommand, userId: UUID): TransactionDto
    fun getBalance(): List<TokenDto>
}

@Service
class TransactionServiceImpl(
    val transactionRepository: TransactionRepository,
    val projectRepository: ProjectRepository
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

    override fun getBalance(): List<TokenDto> {
        TODO("Not yet implemented")
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