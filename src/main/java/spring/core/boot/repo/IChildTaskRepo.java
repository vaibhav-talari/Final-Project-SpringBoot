package spring.core.boot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.Project;
@Repository
public interface IChildTaskRepo extends JpaRepository<ChildTask,Integer> {
	
	public List<ChildTask> findAllByProject(Project project);

}
