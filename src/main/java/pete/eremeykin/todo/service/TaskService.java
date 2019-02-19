package pete.eremeykin.todo.service;

import pete.eremeykin.todo.model.Task;

import java.util.List;

public interface TaskService {

  void save(Task task);

  List<Task> findByUserId(Long userId);

}
