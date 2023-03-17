package etify.porto.hackathon.project

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

interface ProjectService {
    fun getProjects(): Collection<ProjectDto>
    fun getProject(projectId: UUID): ProjectDto
    fun postProjects(command: CreateProjectCommand): ProjectDto
    fun putProjects(projectId: UUID, command: CreateProjectCommand): ProjectDto
}

@Service
class ProjectServiceImpl(val projectRepository: ProjectRepository) : ProjectService {

    override fun getProjects(): Collection<ProjectDto> {
        return projectRepository.findAll().map { it.toDto() }
    }

    override fun getProject(projectId: UUID): ProjectDto {
        val response = projectRepository.findByIdOrNull(projectId) ?: throw NoSuchElementException()
        return response.toDto()
    }

    override fun postProjects(command: CreateProjectCommand): ProjectDto {
        val newProject = Project(
                UUID.randomUUID(),
                command.name,
                command.logo,
                command.status,
                command.websiteURL,
                command.twitterURL,
                command.telegramURL,
                command.mediumURL,
                command.tokenContractAddress,
        )
        newProject.tokens = command.tokenList.map { it.toDomain(newProject)}.toMutableList()
        projectRepository.save(newProject)
        return newProject.toDto()
    }

    private fun CreateTokenCommand.toDomain(project: Project ): Token {
        return Token(UUID.randomUUID(), name, tokenAddress, symbol, chain, project)
    }
    override fun putProjects(projectId: UUID, command: CreateProjectCommand): ProjectDto {
        val newProject = Project(projectId, command.name, command.logo, command.status, command.websiteURL, command.twitterURL, command.telegramURL, command.mediumURL, command.tokenContractAddress)
        newProject.tokens = command.tokenList.map { it.toDomain(newProject)}.toMutableList()
        projectRepository.save(newProject)
        return newProject.toDto()

    }

    private fun Project.toDto(): ProjectDto {
        return ProjectDto(
                id = id,
                name = name,
                logo = logo,
                status = status,
                websiteURL = websiteURL,
                twitterURL = twitterURL,
                telegramURL = telegramURL,
                mediumURL = mediumURL,
                tokenContractAddress = tokenContractAddress,
                tokenList = tokens.map { it.toDto() }
        )
    }

    private fun ProjectDto.toDomain(): Project {
        val project = Project(
                id = id,
                name = name,
                logo = logo,
                status = status,
                websiteURL = websiteURL,
                twitterURL = twitterURL,
                telegramURL = telegramURL,
                mediumURL = mediumURL,
                tokenContractAddress = tokenContractAddress
        )
        project.tokens = tokenList.map { it.toDomain(project) }.toMutableList()
        return project
    }

    private fun Token.toDto(): TokenDto {
        return TokenDto(
                id = id,
                name = name,
                tokenAddress = tokenAddress,
                symbol = symbol,
                chain = chain
        )
    }

    private fun TokenDto.toDomain(project: Project): Token {
        return Token(
                id = UUID.randomUUID(),
                name = name,
                tokenAddress = tokenAddress,
                symbol = symbol,
                chain = chain,
                project = project
        )
    }
}

