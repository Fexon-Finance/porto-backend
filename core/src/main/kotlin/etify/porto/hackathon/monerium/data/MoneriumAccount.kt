package etify.porto.hackathon.monerium.data

data class MoneriumAccount(
    val id: String,
    val address: String,
    val currency: String,
    val chain: String,
    val network: String
)