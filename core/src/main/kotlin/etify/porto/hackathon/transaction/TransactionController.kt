package etify.porto.hackathon.transaction

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TransactionController(private val service: TransactionService) {

    @GetMapping("/api/wallet/{userId}/transactions")
    fun getTransaction(@PathVariable userId: UUID): Collection<TransactionDto> = service.getTransactions(userId)

    @PostMapping("/api/wallet/{userId}/transactions")
    fun initTransaction(@RequestBody command: CreateTransactionCommand, @PathVariable userId: UUID): TransactionDto = service.initTransaction(command, userId)
}