package etify.porto.hackathon.account

data class CreateAccountCommand(
    val name: String,
    val surname: String,
    val email: String
)