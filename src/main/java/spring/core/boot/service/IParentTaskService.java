package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import spring.core.boot.model.ParentTask;

public interface IParentTaskService {
	
	public int saveParentTask(ParentTask parentTask);
	public Optional<ParentTask> getParentTask(int parentTaskID);
	public List<ParentTask> getAllParentTasks();
	public boolean deleteParentTask(int parentTaskID);
	
	public Optional<ParentTask> getParentTaskByName(String taskName);

}
