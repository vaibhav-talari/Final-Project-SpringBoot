package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.model.Project;
import spring.core.boot.model.User;
import spring.core.boot.repo.IChildTaskRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ChildTaskRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IChildTaskRepo childTaskRepo;
	
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
		for(ChildTask child:childtasks) 
			entityManager.persist(child);
		
	}
	
	@After
	public void tearDown() {
		for(ChildTask child:childtasks) 
			entityManager.remove(child);
	}
	
	@Test
	public void whenFindChildTaskByProjectName() {
		List<ChildTask> actualChildTasks=childTaskRepo.findAllByProject(childtasks[4].getProject());
		List<ChildTask> expectedChildTask=Arrays.asList(childtasks[4],childtasks[5],childtasks[6],childtasks[7]);
		assertThat(actualChildTasks).isEqualTo(expectedChildTask);

	}

}
