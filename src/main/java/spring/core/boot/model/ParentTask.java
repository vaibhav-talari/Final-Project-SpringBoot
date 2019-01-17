package spring.core.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="parenttasks")
public class ParentTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int parentTaskID;
	
	@NotEmpty
	private String parentTaskName;
	
	ParentTask(){}

	public ParentTask(int parentTaskID, @NotEmpty String parentTaskName) {
		super();
		this.parentTaskID = parentTaskID;
		this.parentTaskName = parentTaskName;
	}

	public ParentTask(@NotEmpty String parentTaskName) {
		super();
		this.parentTaskName = parentTaskName;
	}


	public int getParentTaskID() {
		return parentTaskID;
	}

	public void setParentTaskID(int parentTaskID) {
		this.parentTaskID = parentTaskID;
	}

	public String getParentTaskName() {
		return parentTaskName;
	}

	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}

	@Override
	public String toString() {
		return "ParentTask [parentTaskID=" + parentTaskID + ", parentTaskName=" + parentTaskName + "]";
	}

	

}
