package spring.core.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import spring.core.boot.model.ParentTask;
import spring.core.boot.restapi.ParentTaskControlAPI;
import spring.core.boot.service.IParentTaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentTaskControlAPI.class)
public class ParentTaskControlAPIUnitTest {

	@MockBean
	private IParentTaskService parentTaskSerice;

	@Autowired
	private MockMvc mockmvc;
	

	@Test
	public void whenGetAllParentTaskRequest_returnAJSONArray() throws Exception {
		
		
		ParentTask[] tasks = new ParentTask[] {
				new ParentTask("Meet Manager"),				
				new ParentTask("Complete Database"), 				
				new ParentTask("Submit Reposts") };
		Mockito.when(parentTaskSerice.getAllParentTasks())
		.thenReturn(Arrays.asList(tasks));
		
		mockmvc.perform(get("/parent/get-all-parent-tasks").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].parentTaskName",is("Meet Manager")))
		.andExpect(jsonPath("$[1].parentTaskName",is("Complete Database")))
		.andExpect(jsonPath("$[2].parentTaskName",is("Submit Reposts")));

	}
	
	@Test
	public void whenGetParentTaskRequest_returnAJSONArray() throws Exception
	{
		ParentTask tasks =new ParentTask("Meet Manager");		
		ParentTask Value = tasks;
		Optional<ParentTask> returnValue = Optional.of((ParentTask) Value);
		Mockito.<Optional<ParentTask>>when(parentTaskSerice.getParentTask(tasks.getParentTaskID()))
		.thenReturn(returnValue);
		
		mockmvc.perform(get("/parent/get-parent-task/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.parentTaskName",is(tasks.getParentTaskName())));
		
	}
	
	@Test
	public void whenParentTaskPosted() throws Exception{
		String jsonString="{\"parentTaskName\":\"Achieve By tomorrow\"}";

		Mockito.when(parentTaskSerice.saveParentTask(Mockito.any()))
		.thenReturn(1);
		
		RequestBuilder req = MockMvcRequestBuilders
				.post("/parent/add-parent-task")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.parentTaskID",is(1)))
		.andExpect(jsonPath("$.parentTaskName",is("Achieve By tomorrow")));
	}
	
	@Test
	public void whenParentTaskPut() throws Exception{
		
		ParentTask tasks =new ParentTask("Meet Manager");		
		ParentTask Value = tasks;
		Optional<ParentTask> returnValue = Optional.of((ParentTask) Value);
		Mockito.<Optional<ParentTask>>when(parentTaskSerice.getParentTask(tasks.getParentTaskID()))
		.thenReturn(returnValue);

		String jsonString="{\"parentTaskName\":\"Updated Meet Director\"}";

		Mockito.when(parentTaskSerice.saveParentTask(Mockito.any()))
		.thenReturn(0);
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/parent/edit-parent-task/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.parentTaskName",is("Updated Meet Director")));
		
	}
	
	@Test
	public void whenDeletParentTaskRequest_returnNULLArray() throws Exception
	{
		ParentTask tasks = new ParentTask("Meet Manager");			
		
		Mockito.when(parentTaskSerice.deleteParentTask(tasks.getParentTaskID()))
		.thenReturn(true);
		
		mockmvc.perform(MockMvcRequestBuilders.delete("/parent/delete-parent-task/0")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());				
		
	}
}
