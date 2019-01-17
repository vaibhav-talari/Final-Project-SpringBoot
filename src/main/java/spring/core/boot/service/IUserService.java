package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import spring.core.boot.model.User;

public interface IUserService {
	public int saveUser(User user);
	public Optional<User> getUser(int userID);
	public List<User> getAllUsers();
	public boolean deleteUser(int userID);
	
	public Optional<User> getUserByEmployeeId(long empId);
}
