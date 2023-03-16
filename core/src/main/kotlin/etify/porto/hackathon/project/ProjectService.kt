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
        val newProject = ProjectDto(UUID.randomUUID(), command.name, command.logo, command.status, command.websiteURL, command.twitterURL, command.telegramURL, command.mediumURL, command.tokenContractAddress)
        projectRepository.save(newProject.toDomain())
        return newProject
    }

    override fun putProjects(projectId: UUID, command: CreateProjectCommand): ProjectDto {
        val newProject = ProjectDto(UUID.randomUUID(), command.name, command.logo, command.status, command.websiteURL, command.twitterURL, command.telegramURL, command.mediumURL, command.tokenContractAddress)
        projectRepository.save(newProject.toDomain())
        return newProject
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
                tokenContractAddress = tokenContractAddress
        )
    }

    private fun ProjectDto.toDomain(): Project {
        return Project(
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
    }
}

