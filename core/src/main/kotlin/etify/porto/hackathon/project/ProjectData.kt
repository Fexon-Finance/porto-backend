package etify.porto.hackathon.project

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
data class ProjectDto(
        val id: UUID,
        val name: String,
        val status: ProjectStatus,
        val websiteURL: String,
        val twitterURL: String,
        val telegramURL: String,
        val mediumURL: String,
        val tokenContractAddress: String
)

data class CreateProjectCommand(
        val name: String,
        val status: ProjectStatus,
        val websiteURL: String,
        val twitterURL: String,
        val telegramURL: String,
        val mediumURL: String,
        val tokenContractAddress: String
)

@Entity
@Table(name = "projects", schema = "porto")
data class Project(
        @Id
        @Column(name = "id")
        val id: UUID,

        @Column(name = "name")
        val name: String,

        @Column(name = "status")
        val status: ProjectStatus,

        @Column(name = "website_url")
        val websiteURL: String,

        @Column(name = "twitter_url")
        val twitterURL: String,

        @Column(name = "telegram_url")
        val telegramURL: String,

        @Column(name = "medium_url")
        val mediumURL: String,

        @Column(name = "token_contract_address")
        val tokenContractAddress: String
)

enum class ProjectStatus {
        VERIFIED,
        NOT_VERIFIED,
        IN_PROGRESS
}