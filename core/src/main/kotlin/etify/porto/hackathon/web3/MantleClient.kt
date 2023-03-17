package etify.porto.hackathon.web3

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.math.BigInteger

interface MantleClient {
    fun createProject(projectId: String)
}

@Service
class MantleClientImpl(
    @Value("\${mantle.rpc}") private val rpc: String,
    @Value("\${mantle.private-key}") private val privateKey: String
) : MantleClient {
    companion object {
        private const val CONTRACT_ADDRESS = "0xe5431dfE8623622111424E0d42cAD8552E67F2D0"
    }


    private val client = initializeClient()
    private val wallet = initializeWallet()
    private val transactionManager = RawTransactionManager(client, wallet, 5001)
    private val poller = PollingTransactionReceiptProcessor(
        client,
        TransactionManager.DEFAULT_POLLING_FREQUENCY,
        TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
    )

    private fun initializeClient(): Web3j {
        val service = HttpService(rpc, false)
        return Web3j.build(service)
    }

    private fun initializeWallet(): Credentials {
        return Credentials.create(privateKey)
    }

    @PostConstruct
    private fun init() {
        createProject("Test")
    }

    override fun createProject(projectId: String) {
        val function = Function(
            "createProject",
            listOf(Utf8String(projectId)),
            listOf()
        )

        val encoded = FunctionEncoder.encode(function)

        val transaction = transactionManager.sendTransaction(
            BigInteger.valueOf(1),
            DefaultGasProvider.GAS_LIMIT,
            CONTRACT_ADDRESS,
            encoded,
            BigInteger.ZERO
        )

        poller.waitForTransactionReceipt(transaction.transactionHash)
    }

}
