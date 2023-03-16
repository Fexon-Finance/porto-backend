package etify.porto.hackathon.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class ProjectController(private val service: ProjectService) {

    @GetMapping("/api/projects")
    fun getProjects(): Collection<ProjectDto> = service.getProjects()

    @GetMapping("/api/project/{projectId}")
    fun getProjects(@PathVariable projectId: UUID): ProjectDto = service.getProject(projectId)

    @PostMapping("/api/project")
    fun postProjects(@RequestBody command: CreateProjectCommand): ProjectDto = service.postProjects(command)

    @PutMapping("/api/project/{projectId}")
    fun postProjects(@PathVariable projectId: UUID, @RequestBody command: CreateProjectCommand): ProjectDto = service.putProjects(projectId, command)
}
