package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.User;
import spring.core.boot.repo.IUserRepo;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepo userRepo;

	@Override
	public int saveUser(User user) {
		if(userRepo.findByEmployeeID(user.getEmployeeID()).isPresent()){
			Optional<User> availuser=userRepo.findById(user.getUserID());
			availuser.get().setFirstName(user.getFirstName());
			availuser.get().setLastName(user.getLastName());
			User isUserSaved=userRepo.save(availuser.get());
			return isUserSaved.getUserID();
		}else {
		User isUserSaved=userRepo.save(user);
		return isUserSaved.getUserID();
	}
	}

	@Override
	public Optional<User> getUser(int userID) {
		Optional<User> getUser=userRepo.findById(userID);
		return getUser;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public boolean deleteUser(int userID) {
		boolean isDeleted=false;
		if(userRepo.findById(userID).isPresent()) {
			userRepo.deleteById(userID);
			isDeleted=true;
		}return isDeleted;
	}

	@Override
	public Optional<User> getUserByEmployeeId(long empId) {
		return userRepo.findByEmployeeID(empId);
	}

}
