package etify.porto.hackathon.account

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "accounts", schema = "porto")
class Account(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "surname")
    val surname: String,

    @Column(name = "iban")
    val iban: String,

    @Column(name = "public_key")
    val publicKey: String,

    @Column(name = "wallet_address")
    val walletAddress: String
)
