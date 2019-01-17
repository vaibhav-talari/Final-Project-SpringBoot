package spring.core.boot.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.core.boot.model.User;
import spring.core.boot.service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserControlAPI {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/get-all-users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/get-user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") int userID) {
		Optional<User> isUser = userService.getUser(userID);
		ResponseEntity<User> response = null;
		if (isUser.isPresent())
			response = new ResponseEntity<>(isUser.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}

	@PostMapping("/add-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		ResponseEntity<User> response = null;
		if (user.equals(null)) {
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			int UserId = userService.saveUser(user);
			user.setUserID(UserId);
			response = new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		return response;
	}

	@PutMapping("/edit-user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int userID,
			@RequestBody User newUser) {
		Optional<User> oldUser = userService.getUser(userID);
		ResponseEntity<User> response = null;
		if (oldUser.isPresent()) {
			
			oldUser.get().setFirstName(newUser.getFirstName());
			oldUser.get().setLastName(newUser.getLastName());
			oldUser.get().setEmployeeID(newUser.getEmployeeID());
			
			userService.saveUser(oldUser.get());
			response = new ResponseEntity<>(oldUser.get(),HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int userID) {
		ResponseEntity<Void> response = null;
		if (userService.deleteUser(userID))
			response = new ResponseEntity<>(HttpStatus.ACCEPTED);
		else
			response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return response;
	}

}
