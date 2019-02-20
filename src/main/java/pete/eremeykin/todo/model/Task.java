package pete.eremeykin.todo.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskId;
  private long userId;
  private String taskText;
  private int taskOrder;
  private boolean taskCompleted;

  @Override
  public String toString() {
    return "Task{" +
        "taskId=" + taskId +
        ", userId=" + userId +
        ", taskText='" + taskText + '\'' +
        ", taskOrder=" + taskOrder +
        ", taskCompleted=" + taskCompleted +
        '}';
  }

  public static List<Task> fromJson(String jsonString, Account hostAccount) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(jsonString);
    List<Task> resultList = new ArrayList<>(root.size());
    for (int i = 0; i < root.size(); i++) {
      JsonNode currentNode = root.get(i);
      Task newTask = new Task();
      newTask.taskText = currentNode.get("title").asText();
      newTask.taskOrder = i;
      newTask.taskCompleted = currentNode.get("completed").asBoolean();
      newTask.userId = hostAccount.getAccountId();
      resultList.add(newTask);
    }
    return resultList;
  }


  public static String toJson(List<Task> taskList) {
    StringBuilder resultJson = new StringBuilder();
    resultJson.append("[");
    Iterator<Task> taskIter = taskList.iterator();
    while (taskIter.hasNext()) {
      Task task = taskIter.next();
      resultJson.append("{");
      resultJson.append("\"id\":");
      resultJson.append(task.taskId);
      resultJson.append(",");
      resultJson.append("\"title\":");
      resultJson.append("\"");
      resultJson.append(task.taskText);
      resultJson.append("\"");
      resultJson.append(",");
      resultJson.append("\"completed\":");
      resultJson.append(task.taskCompleted);
      resultJson.append("}");
      if (taskIter.hasNext()) {
        resultJson.append(",");
      }
    }
    resultJson.append("]");
    return resultJson.toString();
  }
}
