package pete.eremeykin.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskId;
  private long userId;
  private String taskText;
  private int taskOrder;

  @Override
  public String toString() {
    return "Task{" +
        "taskId=" + taskId +
        ", userId=" + userId +
        ", taskText='" + taskText + '\'' +
        ", taskOrder=" + taskOrder +
        '}';
  }
}
