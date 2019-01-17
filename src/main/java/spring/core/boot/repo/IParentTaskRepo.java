package spring.core.boot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.core.boot.model.ParentTask;
@Repository
public interface IParentTaskRepo extends JpaRepository<ParentTask,Integer> {
	public Optional<ParentTask> findByParentTaskName(String taskName);

}
