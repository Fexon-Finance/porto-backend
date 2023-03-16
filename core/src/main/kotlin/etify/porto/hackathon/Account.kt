package etify.porto.hackathon

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID


@Entity
@Table(name = "accounts", schema = "porto")
class Account(
    @Id
    @Column(name = "id")
    val id: UUID,

    )
