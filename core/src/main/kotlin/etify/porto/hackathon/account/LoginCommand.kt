package etify.porto.hackathon.account

data class LoginCommand(
    val email: String,
    val password: String
)