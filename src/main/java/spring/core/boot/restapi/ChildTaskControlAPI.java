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

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.Project;
import spring.core.boot.service.IChildTaskService;
import spring.core.boot.service.IProjectService;

@RestController
@RequestMapping("/child")
@CrossOrigin
public class ChildTaskControlAPI {
	
	@Autowired
	private IChildTaskService childTaskService;
	
	@Autowired
	private IProjectService projectService;
	
	@GetMapping("/get-all-child-tasks")
	public ResponseEntity<List<ChildTask>> getAllChildTask() {
		return new ResponseEntity<>(childTaskService.getAllChildTask(), HttpStatus.OK);
	}

	@GetMapping("/get-child-task/{id}")
	public ResponseEntity<ChildTask> getChildTask(@PathVariable("id") int childTaskID) {
		Optional<ChildTask> isChildTask = childTaskService.getChildTask(childTaskID);
		ResponseEntity<ChildTask> response = null;
		if (isChildTask.isPresent())
			response = new ResponseEntity<>(isChildTask.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}
	
	@GetMapping("/get-child-task-by-project/{id}")
	public ResponseEntity<List<ChildTask>> getChildTaskByProjectName(@PathVariable("id") int projectID) {
		Optional<Project> getProject=projectService.getProject(projectID);
		if(getProject.isPresent()) {
			List<ChildTask> isChildTask = childTaskService.getAllChildTaskByProject(getProject.get());
			return new ResponseEntity<>(isChildTask, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.OK);
		}
		
	}

	@PostMapping("/add-child-task")
	public ResponseEntity<ChildTask> createChildTask(@RequestBody ChildTask childTask) {
		ResponseEntity<ChildTask> response = null;
		if (childTask.equals(null)) {
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			int childId = childTaskService.saveChildTask(childTask);
			childTask.setChildTaskID(childId);;
			response = new ResponseEntity<>(childTask, HttpStatus.CREATED);
		}
		return response;
	}

	@PutMapping("/edit-child-task/{id}")
	public ResponseEntity<ChildTask> updateChildTask(@PathVariable("id") int childTaskID,
			@RequestBody ChildTask newChildTask) {
		Optional<ChildTask> oldChildTask = childTaskService.getChildTask(childTaskID);
		ResponseEntity<ChildTask> response = null;
		if (oldChildTask.isPresent()) {
			
			oldChildTask.get().setChildTaskName(newChildTask.getChildTaskName());
			oldChildTask.get().setTaskStartDate(newChildTask.getTaskStartDate());
			oldChildTask.get().setTaskEndDate(newChildTask.getTaskEndDate());
			oldChildTask.get().setTaskPriority(newChildTask.getTaskPriority());
			oldChildTask.get().setTaskStatus(newChildTask.isTaskStatus());
			oldChildTask.get().setParent(newChildTask.getParent());
			
			childTaskService.saveChildTask(oldChildTask.get());
			response = new ResponseEntity<>(oldChildTask.get(),HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/delete-child-task/{id}")
	public ResponseEntity<Void> deleteChildTask(@PathVariable("id") int childTaskID) {
		ResponseEntity<Void> response = null;
		if (childTaskService.deletechildTask(childTaskID))
			response = new ResponseEntity<>(HttpStatus.ACCEPTED);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return response;
	}

}
