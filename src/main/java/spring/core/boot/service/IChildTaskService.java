package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.Project;

public interface IChildTaskService {

	public int saveChildTask(ChildTask childTask);
	public Optional<ChildTask> getChildTask(int childTaskID);
	public List<ChildTask> getAllChildTask();
	public boolean deletechildTask(int childTaskID);
	
	public List<ChildTask> getAllChildTaskByProject(Project project);
}
