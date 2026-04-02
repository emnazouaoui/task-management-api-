package wevioo.example.taskmanagement.Service.Impl;

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
import wevioo.example.taskmanagement.Service.TaskService;
import wevioo.example.taskmanagement.entity.Task;
import wevioo.example.taskmanagement.entity.TaskStatus;
import wevioo.example.taskmanagement.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    // Constructor Injection
    public TaskServiceImpl(TaskRepository taskRepository,UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Page<TaskDTO> getTasksWithPagination(int page, int size,String sortBy) {
        logger.info("Fetching all tasks with pagination and sorting");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        logger.info("Number of tasks fetched successfully: {}", size);
        return  taskRepository.findAll(pageable)
                .map(taskMapper::toDTO);
         //logger.debug("Number of tasks fetched: {}", tasks.size());
    }

//    @Override
//    public List<TaskDTO> getAllTasks() {
//       //return List.of();
//        return taskRepository.findAll()
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        logger.info("Create new task");
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        Task savedTask = taskRepository.save(task);
        //return mapToDTO(savedTask);
        logger.info("Task created successfully");
        return taskMapper.toDTO(savedTask);

    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        logger.info("Update task");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        logger.info("Task updated with id {} not found", task.getId());
        //logger.debug("Task updated with id {} not found", task.getId());
        logger.error("Error occurred while processing update task", new RuntimeException("Task not found"));
        // ✅ update fields
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        if (taskDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        }
        Task updatedTask = taskRepository.save(task);
        //return mapToDTO(updatedTask);
        logger.info("Task Updated successfully");
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        logger.info("Delete task");
        taskRepository.deleteById(id);
        logger.info("Task deleted successfully with id {}", id);
    }

    @Override
    public TaskDTO updateStatus(Long taskId, String status) {
        //return null;
        logger.info("Update task status");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        logger.info("Task status updated with id {} not found", task.getId());
        //logger.debug("Task status updated with id {} not found", task.getId());
        logger.error("Error occurred while processing update status", new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));
        Task updatedTask = taskRepository.save(task);
        //return mapToDTO(updatedTask);
        logger.info("Task status updated successfully");
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public TaskDTO assignTask(Long taskId, Long userId) {
        logger.info("Assign task to user");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        logger.info("Task with id {} not found", task.getId());
        //logger.debug("Task with id {} not found", task.getId());
        logger.error("Error occurred while processing assign task", new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("user with id {} not found", user.getId());
        //logger.debug("user with id {} not found", user.getId());
        logger.error("Error occurred while processing assign task", new RuntimeException("user not found"));
        // assign
        task.setAssignedUser(user);
        Task updatedTask = taskRepository.save(task);
        //return mapToDTO(updatedTask);
        logger.info("Task {} assigned to user {} successfully",task.getId(),user.getId());
        return taskMapper.toDTO(updatedTask);
    }

    //Search tasks by status, title and description
//    @Override
//    public Page<TaskDTO> searchTasks(String status, String title, String description, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        TaskStatus taskStatus = null;
//        if (status != null) {
//            taskStatus = TaskStatus.valueOf(status.toUpperCase());
//        }
//        return taskRepository.searchTasks(taskStatus, title, description, pageable)
//                .map(taskMapper::toDTO);
//    }
        @Override
        public List<TaskDTO> searchTasks(String keyword) {
            logger.info("Search tasks");
            List<Task> tasks = taskRepository.searchTasks(keyword);
            logger.info("Number of tasks fetched successfully: {}", tasks.size());
            return taskMapper.toDTOList(tasks);
        }



//    // Mapper (Entity → DTO)
//    private TaskDTO mapToDTO(Task task) {
//        TaskDTO dto = new TaskDTO();
//        dto.setId(task.getId());
//        dto.setTitle(task.getTitle());
//        dto.setDescription(task.getDescription());
//        dto.setStatus(task.getStatus().name());
//        if (task.getAssignedUser() != null) {
//            dto.setAssignedUserId(task.getAssignedUser().getId());
//        }
//        return dto;
//    }
}
