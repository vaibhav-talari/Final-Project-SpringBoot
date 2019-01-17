package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.repo.IChildTaskRepo;

@Service
public class ChildTaskServiceImpl implements IChildTaskService {
	
	@Autowired
	private IChildTaskRepo childTaskRepo;
	@Autowired
	private IParentTaskService parentTaskService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IUserService userService;
		

	@Override
	public int saveChildTask(ChildTask childTask) {			
		Optional<Project> availproject=projectService.getProject(childTask.getProject().getProjectID());
		Optional<User> availuser=userService.getUser(childTask.getUser().getUserID());
		Optional<ParentTask> availparent=parentTaskService.getParentTask(childTask.getParent().getParentTaskID());
		childTask.setProject(availproject.get());
		childTask.setUser(availuser.get());
		childTask.setParent(availparent.get());
		ChildTask isChildTaskSaved=childTaskRepo.save(childTask);
		return isChildTaskSaved.getChildTaskID();
		}
	

	@Override
	public Optional<ChildTask> getChildTask(int childTaskID) {
		Optional<ChildTask> getChildTask=childTaskRepo.findById(childTaskID);
		return getChildTask;
	}

	@Override
	public List<ChildTask> getAllChildTask() {
		return childTaskRepo.findAll();
	}

	@Override
	public boolean deletechildTask(int childTaskID) {
		boolean isDeleted=false;
		if(childTaskRepo.findById(childTaskID).isPresent()) {
			childTaskRepo.deleteById(childTaskID);
			isDeleted=true;
		}return isDeleted;
	}


	@Override
	public List<ChildTask> getAllChildTaskByProject(Project project) {
		return childTaskRepo.findAllByProject(project);
	}

}
