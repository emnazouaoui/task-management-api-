package wevioo.example.taskmanagement.Service;

import org.springframework.data.domain.Page;
import wevioo.example.taskmanagement.DTO.TaskDTO;

import java.util.List;

public interface TaskService {

    Page<TaskDTO> getTasksWithPagination(int page, int size, String sortBy);
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
    TaskDTO updateStatus(Long taskId, String status);
    TaskDTO assignTask(Long taskId, Long userId);
    List<TaskDTO> searchTasks(String keyword);
}
