package spring.core.boot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.core.boot.model.User;
@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {
	
}
