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

import spring.core.boot.model.ParentTask;
import spring.core.boot.service.IParentTaskService;

@CrossOrigin
@RestController
@RequestMapping("/parent")
public class ParentTaskControlAPI {

	@Autowired
	private IParentTaskService parentTaskService;

	@GetMapping("/get-all-parent-tasks")
	public ResponseEntity<List<ParentTask>> getAllParentTask() {
		return new ResponseEntity<>(parentTaskService.getAllParentTasks(), HttpStatus.OK);
	}

	@GetMapping("/get-parent-task/{id}")
	public ResponseEntity<ParentTask> getParentTask(@PathVariable("id") int parentTaskID) {
		Optional<ParentTask> isParentTask = parentTaskService.getParentTask(parentTaskID);
		ResponseEntity<ParentTask> response = null;
		if (isParentTask.isPresent())
			response = new ResponseEntity<>(isParentTask.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}

	@PostMapping("/add-parent-task")
	public ResponseEntity<ParentTask> createParentTask(@RequestBody ParentTask parentTask) {
		ResponseEntity<ParentTask> response = null;
		if (parentTask.equals(null)) {
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			int parentId = parentTaskService.saveParentTask(parentTask);
			parentTask.setParentTaskID(parentId);
			response = new ResponseEntity<>(parentTask, HttpStatus.CREATED);
		}
		return response;
	}

	@PutMapping("/edit-parent-task/{id}")
	public ResponseEntity<ParentTask> updateParentTask(@PathVariable("id") int parentTaskID,
			@RequestBody ParentTask newParentTask) {
		Optional<ParentTask> oldParentTask = parentTaskService.getParentTask(parentTaskID);
		ResponseEntity<ParentTask> response = null;
		if (oldParentTask.isPresent()) {
			oldParentTask.get().setParentTaskName(newParentTask.getParentTaskName());
			parentTaskService.saveParentTask(oldParentTask.get());
			response = new ResponseEntity<>(oldParentTask.get(),HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/delete-parent-task/{id}")
	public ResponseEntity<Void> deleteParentTask(@PathVariable("id") int ParentTaskID) {
		ResponseEntity<Void> response = null;
		if (parentTaskService.deleteParentTask(ParentTaskID))
			response = new ResponseEntity<>(HttpStatus.ACCEPTED);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return response;
	}

}
