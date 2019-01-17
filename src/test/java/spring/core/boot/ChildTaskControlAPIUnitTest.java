package spring.core.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.restapi.ChildTaskControlAPI;
import spring.core.boot.service.IChildTaskService;
import spring.core.boot.service.IProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(ChildTaskControlAPI.class)
public class ChildTaskControlAPIUnitTest {

	@MockBean
	private IChildTaskService childTaskService;

	@MockBean
	private IProjectService projectService;

	@Autowired
	private MockMvc mockmvc;

	@Test
	public void whenGetAllChildTaskRequest_returnAJSONArray() throws Exception {

		ParentTask[] parentTasks = new ParentTask[] { new ParentTask("Meet Manager"),
				new ParentTask("Complete Database") };
		User[] users = new User[] { new User("Paul", "John", 101), new User("Sam", "Well", 102) };

		Project[] projects = new Project[] {
				new Project("Project 1", LocalDate.now(), LocalDate.now().plusDays(7), 15, false, users[0]),
				new Project("Project 2", LocalDate.now(), LocalDate.now().plusDays(8), 20, false, users[1]) };
		ChildTask[] childtasks = new ChildTask[] {
				new ChildTask("Child Task 1", LocalDate.now().plusDays(1), LocalDate.now().plusDays(15), 10, false,
						projects[0], parentTasks[0], users[0]), // 000
				/*
				 * new ChildTask("Child Task 2",LocalDate.now().plusDays(2),
				 * LocalDate.now().plusDays(16),1,false,projects[0], parentTasks[0],users[1]),
				 */// 001
				new ChildTask("Child Task 3", LocalDate.now().plusDays(3), LocalDate.now().plusDays(15), 15, false,
						projects[0], parentTasks[1], users[0]), // 010
				/*
				 * new ChildTask("Child Task 4",LocalDate.now().plusDays(4),
				 * LocalDate.now().plusDays(16),10,false,projects[0], parentTasks[1],users[1]),
				 */// 011
				new ChildTask("Child Task 5", LocalDate.now().plusDays(5), LocalDate.now().plusDays(17), 10, false,
						projects[1], parentTasks[0], users[0]), // 100
				/*
				 * new ChildTask("Child Task 6",LocalDate.now().plusDays(6),
				 * LocalDate.now().plusDays(18),10,false,projects[1], parentTasks[0],users[1]),
				 */// 101
				new ChildTask("Child Task 7", LocalDate.now().plusDays(7), LocalDate.now().plusDays(19), 10, false,
						projects[1], parentTasks[1], users[0]), // 110
				new ChildTask("Child Task 8", LocalDate.now().plusDays(8), LocalDate.now().plusDays(20), 10, false,
						projects[1], parentTasks[1], users[1])// 111

		};

		Mockito.when(childTaskService.getAllChildTask()).thenReturn(Arrays.asList(childtasks));

		mockmvc.perform(get("/child/get-all-child-tasks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].childTaskName", is("Child Task 1")))
				.andExpect(jsonPath("$[1].project.projectName", is("Project 1")))
				.andExpect(jsonPath("$[2].parent.parentTaskName", is("Meet Manager")))
				.andExpect(jsonPath("$[3].user.employeeID", is(101)));

	}

	@Test
	public void whenGetChildTaskRequest_returnAJSONArray() throws Exception {
		ParentTask parentTask = new ParentTask("Complete Database");
		User user = new User("Sam", "Well", 102);

		Project project = new Project("Project 2", LocalDate.now(), LocalDate.now().plusDays(8), 20, false, user);
		ChildTask childtasks = new ChildTask("Child Task 1", LocalDate.now().plusDays(1), LocalDate.now().plusDays(15),
				10, false, project, parentTask, user);

		Optional<ChildTask> returnValue = Optional.of((ChildTask) childtasks);
		Mockito.<Optional<ChildTask>>when(childTaskService.getChildTask(childtasks.getChildTaskID()))
				.thenReturn(returnValue);

		mockmvc.perform(get("/child/get-child-task/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.childTaskName", is(childtasks.getChildTaskName())));

	}

	@Test
	public void whenChildTaskPosted() throws Exception {
		String jsonString = "{\"childTaskName\":\"childTaskName\",\"parent\":{\"parentTaskName\":\"Parent\"}}";

		Mockito.when(childTaskService.saveChildTask(Mockito.any())).thenReturn(1);

		RequestBuilder req = MockMvcRequestBuilders.post("/child/add-child-task").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(jsonString);

		mockmvc.perform(req).andExpect(status().isCreated()).andExpect(jsonPath("$.childTaskName", is("childTaskName")))
				.andExpect(jsonPath("$.parent.parentTaskName", is("Parent")));
	}

	@Test
	public void whenParentTaskPut() throws Exception{
		
		ParentTask parentTask = new ParentTask("Complete Database");
		User user = new User("Sam","Well",102);			
		Project project =new Project("Project 2",LocalDate.now(),LocalDate.now().plusDays(8),20,false,user);
		ChildTask childtasks=new ChildTask("Child Task 1",LocalDate.now().plusDays(1),
						LocalDate.now().plusDays(15),10,false,project,
						parentTask,user);
		Optional<ChildTask> returnValue = Optional.of((ChildTask) childtasks);

		Mockito.<Optional<ChildTask>>when(childTaskService.getChildTask(childtasks.getChildTaskID()))
		.thenReturn(returnValue);

		String jsonString="{\"childTaskName\":\"Updated Task\",\"parent\":{\"parentTaskName\":\"Updated Parent\"},\"user\":{\"firstName\":\"Name\"},\"project\":{\"projectName\":\"new Project\"}}";
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/child/edit-child-task/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.childTaskName",is("Updated Task")))
		.andExpect(jsonPath("$.parent.parentTaskName",is("Updated Parent")))
		.andExpect(jsonPath("$.user.firstName",is("Sam")))
		.andExpect(jsonPath("$.project.projectName",is("Project 2")));

		
	}

	@Test
	public void whenDeletChildTaskRequest_returnNULLArray() throws Exception {
		ParentTask parentTask = new ParentTask("Complete Database");
		User user = new User("Sam", "Well", 102);

		Project project = new Project("Project 2", LocalDate.now(), LocalDate.now().plusDays(8), 20, false, user);
		ChildTask childtasks = new ChildTask("Child Task 1", LocalDate.now().plusDays(1), LocalDate.now().plusDays(15),
				10, false, project, parentTask, user);

		Mockito.when(childTaskService.deletechildTask(childtasks.getChildTaskID())).thenReturn(true);

		mockmvc.perform(
				MockMvcRequestBuilders.delete("/child/delete-child-task/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
	}
	
	@Test
	public void whenFindChildTasksByProjectName() throws Exception{
		
		ParentTask parentTasks =new ParentTask("Complete Database");
		User users =new User("Sam", "Well", 102);

		Project[] projects = new Project[] {
				new Project("Project 1", LocalDate.now(), LocalDate.now().plusDays(7), 15, false, users),
				new Project("Project 2", LocalDate.now(), LocalDate.now().plusDays(8), 20, false, users) };	
		ChildTask[] childtasks = new ChildTask[] {
				new ChildTask("Child Task 1", LocalDate.now().plusDays(1), LocalDate.now().plusDays(15), 10, false,
						projects[0], parentTasks, users), 
				
				  new ChildTask("Child Task 2",LocalDate.now().plusDays(2),
				  LocalDate.now().plusDays(16),1,false,projects[1], parentTasks,users),
				 
				new ChildTask("Child Task 3", LocalDate.now().plusDays(3), LocalDate.now().plusDays(15), 15, false,
						projects[1], parentTasks, users), 
				
				  new ChildTask("Child Task 4",LocalDate.now().plusDays(4),
				  LocalDate.now().plusDays(16),10,false,projects[1], parentTasks,users),
				new ChildTask("Child Task 5", LocalDate.now().plusDays(5), LocalDate.now().plusDays(17), 10, false,
						projects[0], parentTasks, users), 
		
	};
		
		Optional<Project> returnValue = Optional.of((Project) projects[0]);
		Mockito.<Optional<Project>>when(projectService.getProject(0))
		.thenReturn(returnValue);

		Mockito.when(childTaskService.getAllChildTaskByProject(childtasks[0].getProject()))
		.thenReturn(Arrays.asList(childtasks[0],childtasks[4]));
		
		mockmvc.perform(get("/child/get-child-task-by-project/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].childTaskName", is("Child Task 1")))
		.andExpect(jsonPath("$[1].childTaskName", is("Child Task 5")));
		

}
}
