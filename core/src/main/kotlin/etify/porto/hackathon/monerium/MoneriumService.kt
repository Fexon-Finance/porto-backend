package etify.porto.hackathon.monerium

import etify.porto.hackathon.monerium.data.AddAccountAddressData
import etify.porto.hackathon.monerium.data.PatchAccountData
import etify.porto.hackathon.monerium.data.PatchTreasuryData
import etify.porto.hackathon.web3.Web3
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface MoneriumService {
    fun createAccount()

}

@Service
class MoneriumServiceImpl(
    @Value("\${monerium.authorization}") private val authorization: String,
    @Value("\${monerium.profile-id}") private val profileId: String,
    private val client: MoneriumClient
) : MoneriumService {
    companion object {
        private val CREATE_ACCOUNT_MESSAGE = "I hereby declare that I am the address owner.".encodeToByteArray()
    }

    override fun createAccount() {
        val header = buildAuthorizationHeader()
        val privateKey = Web3.createPrivateKey()

//        println(privateKey)
//        println(message)

        val body = AddAccountAddressData(
            address = Web3.getAddress(privateKey),
            signature = Web3.signMessage(privateKey, CREATE_ACCOUNT_MESSAGE)
        )
        val response = client.addAccountAddress(header, profileId, body)

        val patch2 = client.patchAccountData(
            authorizationHeader = authorization,
            accountId = response.id,
            body = PatchAccountData("3cd75adc-c1dd-11ed-a042-2a5f7fc2c676")
        )

        val patch3 = client.patchTreasuryData(
            authorizationHeader = authorization,
            treasureId = patch2.id,
            body = PatchTreasuryData(patch2.id)
        )
//        val patch3 = client.patchTreasuryData()
    }


    private fun buildAuthorizationHeader() = "Basic $authorization"

}