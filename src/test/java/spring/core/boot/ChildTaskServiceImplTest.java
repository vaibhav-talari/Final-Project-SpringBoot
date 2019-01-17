package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.repo.IChildTaskRepo;
import spring.core.boot.service.ChildTaskServiceImpl;
import spring.core.boot.service.IChildTaskService;
import spring.core.boot.service.IParentTaskService;
import spring.core.boot.service.IProjectService;
import spring.core.boot.service.IUserService;


@RunWith(SpringRunner.class)
public class ChildTaskServiceImplTest {
	
	@Autowired
	private IChildTaskService childTaskService;
	
	@MockBean
	private IChildTaskRepo childTaskRepo;
	
	@MockBean
	private IParentTaskService parentTaskService;
	@MockBean
	private IProjectService projectService;
	@MockBean
	private IUserService userService;
	
	@TestConfiguration
	static class ChildTaskServiceUnitTest{
		@Bean
		public IChildTaskService ChildTaskService(){
			return new ChildTaskServiceImpl();
		}
	}	
		
		private ChildTask[] childtasks;

		@Before
		public void setUp() {
			ParentTask[] parentTasks = new ParentTask[] { new ParentTask("Meet Manager"),
					new ParentTask("Complete Database") };
			User[] users = new User[] { new User("Paul", "John", 101), new User("Sam", "Well", 102) };

			Project[] projects = new Project[] {
					new Project("Project 1", LocalDate.now(), LocalDate.now().plusDays(7), 15, false, users[0]),
					new Project("Project 2", LocalDate.now(), LocalDate.now().plusDays(8), 20, false, users[1]) };
			 childtasks = new ChildTask[] {
					new ChildTask("Child Task 1", LocalDate.now().plusDays(1), LocalDate.now().plusDays(15), 10, false,
							projects[0], parentTasks[0], users[0]), // 000
					new ChildTask("Child Task 2", LocalDate.now().plusDays(2), LocalDate.now().plusDays(16), 1, false,
							projects[0], parentTasks[0], users[1]), // 001
					new ChildTask("Child Task 3", LocalDate.now().plusDays(3), LocalDate.now().plusDays(15), 15, false,
							projects[0], parentTasks[1], users[0]), // 010
					new ChildTask("Child Task 4", LocalDate.now().plusDays(4), LocalDate.now().plusDays(16), 10, false,
							projects[0], parentTasks[1], users[1]), // 011
					new ChildTask("Child Task 5", LocalDate.now().plusDays(5), LocalDate.now().plusDays(17), 10, false,
							projects[1], parentTasks[0], users[0]), // 100
					new ChildTask("Child Task 6", LocalDate.now().plusDays(6), LocalDate.now().plusDays(18), 10, false,
							projects[1], parentTasks[0], users[1]), // 101
					new ChildTask("Child Task 7", LocalDate.now().plusDays(7), LocalDate.now().plusDays(19), 10, false,
							projects[1], parentTasks[1], users[0]), // 110
					new ChildTask("Child Task 8", LocalDate.now().plusDays(8), LocalDate.now().plusDays(20), 10, false,
							projects[1], parentTasks[1], users[1])};// 111
			 Mockito.when(childTaskRepo.findAllByProject(childtasks[0].getProject()))
			 .thenReturn(Arrays.asList(childtasks[0],childtasks[1],childtasks[2],childtasks[3]));
					 
			 
		}
		@Test
		public void whenFindChildTaskByProjectName() {
			List<ChildTask> actualChildTasks=childTaskService.getAllChildTaskByProject(childtasks[0].getProject());
			List<ChildTask> expectedChildTasks=Arrays.asList(childtasks[0],childtasks[1],childtasks[2],childtasks[3]);
			assertThat(actualChildTasks).isEqualTo(expectedChildTasks);

		}
}

