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

import spring.core.boot.model.User;
import spring.core.boot.restapi.UserControlAPI;
import spring.core.boot.service.IUserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserControlAPI.class)
public class UserControlAPIUnitTest {
	
	@MockBean
	private IUserService userService;
	
	@Autowired
	private MockMvc mockmvc;
	
	@Test
	public void whenGetAllUsersRequest_returnAJSONArray() throws Exception {
		
		
		User[] users = new User[] {
				new User("Paul","John",101),				
				new User("Sam","Well",102), 				
				new User("Oxford","Rilley",103) };
		Mockito.when(userService.getAllUsers())
		.thenReturn(Arrays.asList(users));
		
		mockmvc.perform(get("/user/get-all-users").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].firstName",is("Paul")))
		.andExpect(jsonPath("$[1].lastName",is("Well")))
		.andExpect(jsonPath("$[2].employeeID",is(103)));

	}

	
	@Test
	public void whenGetUserRequest_returnAJSONArray() throws Exception
	{
		User user =new User("Paul","John",101);		
		Optional<User> returnValue = Optional.of((User) user);
		Mockito.<Optional<User>>when(userService.getUser(user.getUserID()))
		.thenReturn(returnValue);
		
		mockmvc.perform(get("/user/get-user/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName",is("Paul")));
		
	}
	
	@Test
	public void whenUserPosted() throws Exception{
		String jsonString="{\"firstName\":\"Jack\",\"lastName\":\"Ma\",\"employeeID\":100}";

		Mockito.when(userService.saveUser(Mockito.any()))
		.thenReturn(1);
		
		RequestBuilder req = MockMvcRequestBuilders
				.post("/user/add-user")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName",is("Jack")))
		.andExpect(jsonPath("$.lastName",is("Ma")))
		.andExpect(jsonPath("$.employeeID",is(100)));

	}
	
	@Test
	public void whenUserPut() throws Exception{
		
		User user =new User("Oxford","Rilley",103);		
		Optional<User> returnValue = Optional.of((User) user);
		Mockito.<Optional<User>>when(userService.getUser(user.getUserID()))
		.thenReturn(returnValue);

		String jsonString="{\"firstName\":\"Update Oxford\",\"lastName\":\"Update Rilley\",\"employeeID\":200}";

		Mockito.when(userService.saveUser(Mockito.any()))
		.thenReturn(0);
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/user/edit-user/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.firstName",is("Update Oxford")))
		.andExpect(jsonPath("$.lastName",is("Update Rilley")))
		.andExpect(jsonPath("$.employeeID",is(200)));

		
	}
	
	@Test
	public void whenDeletUserRequest_returnNULLArray() throws Exception
	{

		User users =new User("Paul","John",101);			
		
		Mockito.when(userService.deleteUser(users.getUserID()))
		.thenReturn(true);
		
		mockmvc.perform(MockMvcRequestBuilders.delete("/user/delete-user/0")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());

}
}
