package etify.porto.hackathon.web3

import etify.porto.hackathon.dfns.DfnsService
import etify.porto.hackathon.lifi.TransactionRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.TransactionManager
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.utils.Numeric
import java.math.BigInteger

interface Web3 {
    fun sendTransaction(publicKey: String, transactionRequest: TransactionRequest)
}

@Service
class Web3Impl(
    @Value("\${web3.rpc}") private val rpc: String,
    private val dfnsService: DfnsService
) : Web3 {
    override fun sendTransaction(publicKey: String, transactionRequest: TransactionRequest) {
        val service = HttpService(rpc, false)
        val client = Web3j.build(service)
        val poller = PollingTransactionReceiptProcessor(
            client,
            TransactionManager.DEFAULT_POLLING_FREQUENCY,
            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
        )

        val nonce = client.ethGetTransactionCount(transactionRequest.from, DefaultBlockParameterName.LATEST)
            .send()
            .transactionCount

        val transaction = RawTransaction.createTransaction(
            transactionRequest.chainId.toLong(),
            nonce,
            BigInteger(transactionRequest.gasLimit, 16),
            transactionRequest.data,
            BigInteger(transactionRequest.value, 16),
            transactionRequest.data,
            BigInteger(transactionRequest.gasPrice, 16),
            BigInteger(transactionRequest.gasLimit, 16)
        )

        val encodedTransaction = TransactionEncoder.encode(transaction)
        val signedTransaction = dfnsService.sign(publicKey, encodedTransaction)

        val tx = client.ethSendRawTransaction(Numeric.toHexString(signedTransaction)).send()
        poller.waitForTransactionReceipt(tx.transactionHash)
    }
}
