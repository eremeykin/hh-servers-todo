package pete.eremeykin.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pete.eremeykin.todo.model.Task;
import pete.eremeykin.todo.repository.TaskRepository;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskRepository taskRepository;


  @Override
  public void save(Task task) {
    taskRepository.save(task);
  }

  @Override
  public List<Task> findByUserId(Long userId) {
    return taskRepository.findByUserId(userId);
  }
}
