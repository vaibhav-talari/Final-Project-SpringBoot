package spring.core.boot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.repo.IProjectRepo;

@Service
public class ProjectServiceImpl implements IProjectService {

	@Autowired
	private IProjectRepo projectRepo;
	
	@Autowired
	private	IUserService userService;

	@Override
	public int saveProject(Project project) {
		if(!project.isSetdate()) {
			Optional<User> availuser= userService.getUser(project.getUser().getUserID());
			if(availuser.isPresent()) { 
				project.setProjectStartDate(LocalDate.now());
				project.setProjectEndDate(LocalDate.now().plusDays(1));
				project.setUser(availuser.get());
				Project isProjectSaved = projectRepo.save(project);
				return isProjectSaved.getProjectID();
			}else {	
				project.setProjectStartDate(LocalDate.now());
				project.setProjectEndDate(LocalDate.now().plusDays(1));
				Project isProjectSaved = projectRepo.save(project);
				return isProjectSaved.getProjectID();
			}
			
		}else {
			Optional<User> availuser= userService.getUser(project.getUser().getUserID());
			if(availuser.isPresent()) { 				
				project.setUser(availuser.get());
				Project isProjectSaved = projectRepo.save(project);
				return isProjectSaved.getProjectID();
			}else {				
				Project isProjectSaved = projectRepo.save(project);
				return isProjectSaved.getProjectID();
			}		}
			
				
	}

	@Override
	public Optional<Project> getProject(int projectID) {
		Optional<Project> getProject = projectRepo.findById(projectID);
		return getProject;
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepo.findAll();
	}

	@Override
	public boolean deleteProject(int projectID) {
		boolean isDeleted = false;
		if (projectRepo.findById(projectID).isPresent()) {
			projectRepo.deleteById(projectID);
			isDeleted = true;
		}
		return isDeleted;
	}

}
