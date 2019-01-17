package spring.core.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int UserID;
	
	private String firstName;
	
	private String lastName;
	
	private long employeeID;
	
	User(){}

	public User(int userID, String firstName, String lastName, long employeeID) {
		super();
		UserID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
	}

	public User(String firstName, String lastName, long employeeID) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", firstName=" + firstName + ", lastName=" + lastName + ", employeeID="
				+ employeeID + "]";
	}

	
}
