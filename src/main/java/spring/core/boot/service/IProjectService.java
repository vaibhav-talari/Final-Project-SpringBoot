package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import spring.core.boot.model.Project;

public interface IProjectService {	
	public int saveProject(Project project);
	public Optional<Project> getProject(int projectID);
	public List<Project> getAllProjects();
	public boolean deleteProject(int projectID);

}
