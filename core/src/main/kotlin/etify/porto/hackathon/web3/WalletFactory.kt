package etify.porto.hackathon.web3

import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric

object Web3 {
    fun createPrivateKey(): String {
        val keypair = Keys.createEcKeyPair()
        return "0x${keypair.privateKey.toString(16)}"
    }

    fun signMessage(privateKey: String, message: ByteArray): String {
        val credentials = Credentials.create(privateKey)
        val signature = Sign.signPrefixedMessage(message, credentials.ecKeyPair)
        val signedBytes = ByteArray(65)
        System.arraycopy(signature.r, 0, signedBytes, 0, 32)
        System.arraycopy(signature.s, 0, signedBytes, 32, 32)
        System.arraycopy(signature.v, 0, signedBytes, 64, 1)
        return Numeric.toHexString(signedBytes)
    }

    fun getAddress(privateKey: String): String {
        return Credentials.create(privateKey).address
    }
}
