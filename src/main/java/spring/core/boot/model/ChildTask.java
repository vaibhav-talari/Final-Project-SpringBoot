package spring.core.boot.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="childtasks")
public class ChildTask {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int childTaskID;
	
	private String childTaskName;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate taskStartDate;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate taskEndDate;
	
	private int taskPriority;
	
	private boolean taskStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="projectID")
	private Project project;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="parentTaskID")
	private ParentTask parent;

	@ManyToOne(cascade=CascadeType.ALL)
	private User user;
	
	ChildTask(){}

	public ChildTask(int childTaskID, String childTaskName, LocalDate taskStartDate, LocalDate taskEndDate,
			int taskPriority, boolean taskStatus, Project project, ParentTask parent, User user) {
		super();
		this.childTaskID = childTaskID;
		this.childTaskName = childTaskName;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
		this.taskPriority = taskPriority;
		this.taskStatus = taskStatus;
		this.project = project;
		this.parent = parent;
		this.user = user;
	}

	public ChildTask(String childTaskName, LocalDate taskStartDate, LocalDate taskEndDate, int taskPriority,
			boolean taskStatus, Project project, ParentTask parent, User user) {
		super();
		this.childTaskName = childTaskName;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
		this.taskPriority = taskPriority;
		this.taskStatus = taskStatus;
		this.project = project;
		this.parent = parent;
		this.user = user;
	}

	public int getChildTaskID() {
		return childTaskID;
	}

	public void setChildTaskID(int childTaskID) {
		this.childTaskID = childTaskID;
	}

	public String getChildTaskName() {
		return childTaskName;
	}

	public void setChildTaskName(String childTaskName) {
		this.childTaskName = childTaskName;
	}

	public LocalDate getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(LocalDate taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public LocalDate getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(LocalDate taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public int getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(int taskPriority) {
		this.taskPriority = taskPriority;
	}

	public boolean isTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ParentTask getParent() {
		return parent;
	}

	public void setParent(ParentTask parent) {
		this.parent = parent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ChildTask [childTaskID=" + childTaskID + ", childTaskName=" + childTaskName + ", taskStartDate="
				+ taskStartDate + ", taskEndDate=" + taskEndDate + ", taskPriority=" + taskPriority + ", taskStatus="
				+ taskStatus + ", project=" + project + ", parent=" + parent + ", user=" + user + "]";
	}

	
	
	
	
}
