package wevioo.example.taskmanagement.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wevioo.example.taskmanagement.DTO.TaskDTO;
import wevioo.example.taskmanagement.Mapper.TaskMapper;
import wevioo.example.taskmanagement.Repository.TaskRepository;
import wevioo.example.taskmanagement.Repository.UserRepository;
import wevioo.example.taskmanagement.Model.Task;
import wevioo.example.taskmanagement.Model.TaskStatus;
import wevioo.example.taskmanagement.Model.User;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    // Constructor Injection
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    public Page<TaskDTO> getTasksWithPagination(int page, int size,String sortBy) {
        logger.info("Fetching all tasks with pagination and sorting");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        logger.info("Number of tasks fetched successfully: {}", size);
        return  taskRepository.findAll(pageable)
                .map(taskMapper::toDTO);
         //logger.debug("Number of tasks fetched: {}", tasks.size());
    }



    public TaskDTO createTask(TaskDTO taskDTO) {
        logger.info("Create new task");
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        Task savedTask = taskRepository.save(task);
        logger.info("Task created successfully");
        return taskMapper.toDTO(savedTask);

    }


    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        logger.info("Update task");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        logger.info("Task updated with id {} not found", task.getId());
        //logger.debug("Task updated with id {} not found", task.getId());
        logger.error("Error occurred while processing update task", new RuntimeException("Task not found"));
        // Update fields
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        if (taskDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        }
        Task updatedTask = taskRepository.save(task);
        logger.info("Task Updated successfully");
        return taskMapper.toDTO(updatedTask);
    }


    public void deleteTask(Long id) {
        logger.info("Delete task");
        taskRepository.deleteById(id);
        logger.info("Task deleted successfully with id {}", id);
    }


    public TaskDTO updateStatus(Long taskId, String status) {
        logger.info("Update task status");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        logger.info("Task status updated with id {} not found", task.getId());
        //logger.debug("Task status updated with id {} not found", task.getId());
        logger.error("Error occurred while processing update status", new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));
        Task updatedTask = taskRepository.save(task);
        logger.info("Task status updated successfully");
        return taskMapper.toDTO(updatedTask);
    }


    public TaskDTO assignTask(Long taskId, Long userId) {
        logger.info("Assign task to user");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        //logger.info("Task with id {} not found", task.getId());
        //logger.debug("Task with id {} not found", task.getId());
        //logger.error("Error occurred while processing assign task", new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //logger.info("user with id {} not found", user.getId());
        //logger.debug("user with id {} not found", user.getId());
        //logger.error("Error occurred while processing assign task", new RuntimeException("user not found"));
        // assign
        task.setAssignedUser(user);
        Task updatedTask = taskRepository.save(task);
        logger.info("Task {} assigned to user {} successfully",task.getId(),user.getId());
        return taskMapper.toDTO(updatedTask);
    }


        public List<TaskDTO> searchTasks(String keyword) {
            logger.info("Search tasks");
            List<Task> tasks = taskRepository.searchTasks(keyword);
            logger.info("Number of tasks fetched successfully: {}", tasks.size());
            return taskMapper.toDTOList(tasks);
        }

}
