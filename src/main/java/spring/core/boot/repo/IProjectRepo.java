package spring.core.boot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.core.boot.model.Project;
@Repository
public interface IProjectRepo extends JpaRepository<Project,Integer>{

}
