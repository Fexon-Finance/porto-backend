package etify.porto.hackathon.transaction

import org.springframework.stereotype.Service
import java.util.UUID

interface TransactionService {

    fun getTransactions(userId: UUID): List<TransactionDto>
    fun initTransaction(command: CreateTransactionCommand, userId: UUID): TransactionDto
//    fun getBalance(): Collection<>
}

@Service
class TransactionServiceImpl(val transactionRepository: TransactionRepository) : TransactionService {
    override fun getTransactions(userId: UUID): List<TransactionDto> {
        return transactionRepository.findAll().filter { it.accountId == userId }.map { it.toDto() }
    }

    override fun initTransaction(command: CreateTransactionCommand, userId: UUID): TransactionDto {
        val newTransaction =
            TransactionDto(UUID.randomUUID(), command.price, command.date, command.tokenAmount, command.tokenId, userId, command.symbol, command.logo, command.projectName)
        transactionRepository.save(newTransaction.toDomain())
        return newTransaction
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