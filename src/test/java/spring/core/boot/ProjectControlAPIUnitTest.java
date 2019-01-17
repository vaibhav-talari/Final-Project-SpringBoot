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

import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.restapi.ProjectControlAPI;
import spring.core.boot.service.IProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectControlAPI.class)
public class ProjectControlAPIUnitTest {
	
	@MockBean
	private IProjectService projectService;
	
	@Autowired
	private MockMvc mockmvc;
	
	@Test
	public void whenGetAllProjectsRequest_returnAJSONArray() throws Exception {
		
		User[] users = new User[] {
				new User("Paul","John",101),				
				new User("Sam","Well",102), 				
				new User("Oxford","Rilley",103) };	
		
		Project[] projects = new Project[] {
				new Project("Project 1",LocalDate.now(),LocalDate.now().plusDays(7),15,false,users[0]),
				new Project("Project 2",LocalDate.now(),LocalDate.now().plusDays(8),20,false,users[1]),
				new Project("Project 3",LocalDate.now().plusDays(1),LocalDate.now().plusDays(10),7,false,users[2]),
				new Project("Project 4",LocalDate.now(),LocalDate.now().plusDays(11),1,true,users[2]),
				new Project("Project 5",LocalDate.now().plusDays(3),LocalDate.now().plusDays(20),10,false,users[2]),
				 };
		Mockito.when(projectService.getAllProjects())
		.thenReturn(Arrays.asList(projects));
		
		mockmvc.perform(get("/project/get-all-projects").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].projectName",is("Project 1")))
		.andExpect(jsonPath("$[1].projectEndDate",is(LocalDate.now().plusDays(8).toString())))
		.andExpect(jsonPath("$[2].user.firstName",is(users[2].getFirstName())));

	}

	
	@Test
	public void whenGetProjectRequest_returnAJSONArray() throws Exception
	{
		User user =new User("Paul","John",101);	
		Project project = new Project("Project 1",LocalDate.now(),LocalDate.now().plusDays(7),15,false,user);
		Optional<Project> returnValue = Optional.of((Project) project);
		Mockito.<Optional<Project>>when(projectService.getProject(project.getProjectID()))
		.thenReturn(returnValue);
		
		mockmvc.perform(get("/project/get-project/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.projectName",is("Project 1")));
		
	}
	
	@Test
	public void whenProjectPosted() throws Exception{
		String jsonString="{\"projectName\":\"Project 1\",\"projectPriority\":20,\"suspend\":false,\"user\":{\"firstName\":\"jack\",\"lastName\":\"ma\",\"employeeID\":100}}";
		//date works in POSTMAN
		Mockito.when(projectService.saveProject(Mockito.any()))
		.thenReturn(1);
		
		RequestBuilder req = MockMvcRequestBuilders
				.post("/project/add-project")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.projectName",is("Project 1")))
		.andExpect(jsonPath("$.user.employeeID",is(100)));

	}
	
	@Test
	public void whenProjectPut() throws Exception{
		
		User user =new User("Paul","John",101);	
		Project project = new Project("Project 1",LocalDate.now(),LocalDate.now().plusDays(7),15,false,user);
		Optional<Project> returnValue = Optional.of((Project) project);
		Mockito.<Optional<Project>>when(projectService.getProject(project.getProjectID()))
		.thenReturn(returnValue);

		String jsonString="{\"projectName\":\"Update Project 1\",\"projectPriority\":20,\"suspend\":false,\"user\":{\"firstName\":\"jack\",\"lastName\":\"ma\",\"employeeID\":100}}";

		Mockito.when(projectService.saveProject(Mockito.any()))
		.thenReturn(0);
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/project/edit-project/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.projectName",is("Update Project 1")))
		.andExpect(jsonPath("$.user.employeeID",is(101)));//user does not update as required

		
	}
	
	@Test
	public void whenDeletProjectRequest_returnNULLArray() throws Exception
	{

		User user =new User("Paul","John",101);	
		Project project = new Project("Project 1",LocalDate.now(),LocalDate.now().plusDays(7),15,false,user);	
		
		Mockito.when(projectService.deleteProject(project.getProjectID()))
		.thenReturn(true);
		
		mockmvc.perform(MockMvcRequestBuilders.delete("/project/delete-project/0")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

}

}
