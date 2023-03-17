package etify.porto.hackathon.transaction

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

data class TransactionDto(
        val id: UUID,
        val price: Double,
        val date: OffsetDateTime,
        val tokenAmount: Double,
        val tokenId: UUID,
        val accountId: UUID,
        val symbol: String,
        val logo: String,
        val projectName: String
)

data class CreateTransactionCommand(
        val price: Double,
        val date: OffsetDateTime,
        val tokenAmount: Double,
        val tokenId: UUID,
        val accountId: UUID,
        val symbol: String,
        val logo: String,
        val projectName: String
)

@Entity
@Table(name = "transactions", schema = "porto")
data class Transaction(
        @Id
        @Column(name = "id")
        val id: UUID,

        @Column(name = "price")
        val price: Double,

        @Column(name = "date")
        val date: OffsetDateTime,

        @Column(name = "token_amount")
        val tokenAmount: Double,

        @Column(name = "token_id")
        val tokenId: UUID,

        @Column(name = "account_id")
        val accountId: UUID,

        @Column(name = "symbol")
        val symbol: String,

        @Column(name = "logo")
        val logo: String,

        @Column(name = "project_name")
        val projectName: String
)