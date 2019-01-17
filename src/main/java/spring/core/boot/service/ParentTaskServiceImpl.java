package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.ParentTask;
import spring.core.boot.repo.IParentTaskRepo;

@Service
public class ParentTaskServiceImpl implements IParentTaskService{
	
	@Autowired
	private IParentTaskRepo parentTaskRepo;

	@Override
	public int saveParentTask(ParentTask parentTask) {
		ParentTask isParentTaskSaved=parentTaskRepo.save(parentTask);
		return isParentTaskSaved.getParentTaskID();
	}

	@Override
	public Optional<ParentTask> getParentTask(int parentTaskID) {
		Optional<ParentTask> getParentTask=parentTaskRepo.findById(parentTaskID);
		return getParentTask;
	}

	@Override
	public List<ParentTask> getAllParentTasks() {
		return parentTaskRepo.findAll();
	}

	@Override
	public boolean deleteParentTask(int parentTaskID) {
		boolean isDeleted=false;
		if(parentTaskRepo.findById(parentTaskID).isPresent()) {
			parentTaskRepo.deleteById(parentTaskID);
			isDeleted=true;
		}return isDeleted;
	}

	@Override
	public Optional<ParentTask> getParentTaskByName(String taskName) {
		return parentTaskRepo.findByParentTaskName(taskName);
	}

}
