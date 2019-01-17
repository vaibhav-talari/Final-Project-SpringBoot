package spring.core.boot.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int projectID;
	
	private String projectName;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate projectStartDate;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate projectEndDate;
	
	private int projectPriority;
	
	private boolean suspend;
	
	private boolean setdate;
	
	@OneToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private User user;
	
	Project(){}

	public Project(int projectID, String projectName, LocalDate projectStartDate, LocalDate projectEndDate,
			int projectPriority, boolean suspend, boolean setdate, User user) {
		super();
		this.projectID = projectID;
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectPriority = projectPriority;
		this.suspend = suspend;
		this.setdate = setdate;
		this.user = user;
	}

	public Project(String projectName, LocalDate projectStartDate, LocalDate projectEndDate, int projectPriority,
			boolean suspend, boolean setdate, User user) {
		super();
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectPriority = projectPriority;
		this.suspend = suspend;
		this.setdate = setdate;
		this.user = user;
	}
	

	public Project(String projectName, LocalDate projectStartDate, LocalDate projectEndDate, int projectPriority,
			boolean suspend, User user) {
		super();
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectPriority = projectPriority;
		this.suspend = suspend;
		this.user = user;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocalDate getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(LocalDate projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public LocalDate getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(LocalDate projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public int getProjectPriority() {
		return projectPriority;
	}

	public void setProjectPriority(int projectPriority) {
		this.projectPriority = projectPriority;
	}

	public boolean isSuspend() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	public boolean isSetdate() {
		return setdate;
	}

	public void setSetdate(boolean setdate) {
		this.setdate = setdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Project [projectID=" + projectID + ", projectName=" + projectName + ", projectStartDate="
				+ projectStartDate + ", projectEndDate=" + projectEndDate + ", projectPriority=" + projectPriority
				+ ", suspend=" + suspend + ", setdate=" + setdate + ", user=" + user + "]";
	}

	
}
