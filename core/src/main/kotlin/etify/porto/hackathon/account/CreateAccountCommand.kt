package etify.porto.hackathon.account

import java.time.OffsetDateTime

data class CreateAccountCommand(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)