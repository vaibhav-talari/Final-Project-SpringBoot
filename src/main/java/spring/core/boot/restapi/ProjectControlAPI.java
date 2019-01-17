package spring.core.boot.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.core.boot.model.Project;
import spring.core.boot.service.IProjectService;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectControlAPI {
	
	@Autowired
	private IProjectService projectService;
	
	@GetMapping("/get-all-projects")
	public ResponseEntity<List<Project>> getAllProjects() {
		return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
	}

	@GetMapping("/get-project/{id}")
	public ResponseEntity<Project> getProject(@PathVariable("id") int projectID) {
		Optional<Project> isProject = projectService.getProject(projectID);
		ResponseEntity<Project> response = null;
		if (isProject.isPresent())
			response = new ResponseEntity<>(isProject.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}

	@PostMapping("/add-project")
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		ResponseEntity<Project> response = null;
		if (project.equals(null)) {
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			int projectId = projectService.saveProject(project);
			project.setProjectID(projectId);
			response = new ResponseEntity<>(project, HttpStatus.CREATED);
		}
		return response;
	}

	@PutMapping("/edit-project/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable("id") int projectID,
			@RequestBody Project newProject) {
		Optional<Project> oldProject = projectService.getProject(projectID);
		ResponseEntity<Project> response = null;
		if (oldProject.isPresent()) {
			
			oldProject.get().setProjectName(newProject.getProjectName());
			oldProject.get().setProjectStartDate(newProject.getProjectStartDate());
			oldProject.get().setProjectEndDate(newProject.getProjectEndDate());
			oldProject.get().setSetdate(true);
			oldProject.get().setProjectPriority(newProject.getProjectPriority());
			oldProject.get().setSuspend(newProject.isSuspend());
			
			projectService.saveProject(oldProject.get());
			response = new ResponseEntity<>(oldProject.get(),HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/delete-project/{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable("id") int projectID) {
		ResponseEntity<Void> response = null;
		if (projectService.deleteProject(projectID))
			response = new ResponseEntity<>(HttpStatus.ACCEPTED);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return response;
	}

}
