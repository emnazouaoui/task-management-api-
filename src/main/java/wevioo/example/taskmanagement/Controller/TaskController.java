package wevioo.example.taskmanagement.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import wevioo.example.taskmanagement.DTO.TaskDTO;
import wevioo.example.taskmanagement.Service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task API", description = "CRUD operations for tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    // Constructor Injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Get all tasks with sorting and pagination")
    public Page<TaskDTO> getTasks(
            //@RequestBody()
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        logger.info("GET /tasks called");
        return taskService.getTasksWithPagination(page, size,sortBy);
    }


    // Create tasks
    @PostMapping
    @Operation(summary = "Create task", description = "Create task")
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        logger.info("POST /tasks called");
        return taskService.createTask(taskDTO);
    }

    // Update task by Id
    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Update task: title, description, status")
    public TaskDTO updateTask(@PathVariable Long id,
                              @RequestBody TaskDTO taskDTO) {
        logger.info("PUT tasks/tasks called");
        return taskService.updateTask(id, taskDTO);
    }

    // Delete Task
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task", description = "Delete task by Id")
    public void deleteTask(@PathVariable Long id) {
        logger.info("DELETE /tasks called");
        taskService.deleteTask(id);
    }

    // Update Task Status (TODO / DOING / DONE)
    @PutMapping("/{taskId}/status")
    @Operation(summary = "Update task by Status", description = "Update Task Status (TODO / DOING / DONE)")
    public TaskDTO updateStatus(@PathVariable Long taskId,
                                @RequestParam String status) {
        logger.info("PUT status/tasks called");
        return taskService.updateStatus(taskId, status);
    }

    // Assign task to user
    @PutMapping("/{taskId}/assign/{userId}")
    @Operation(summary = "Assign task to user", description = "Assign task to user")
    public TaskDTO assignTask(@PathVariable Long taskId,
                              @PathVariable Long userId) {
        logger.info("PUT assign task/tasks called");
        return taskService.assignTask(taskId, userId);
    }


    // Search tasks by keyword
    @GetMapping("/search")
    @Operation(summary = "Search tasks", description = "Get tasks with filter by keyword")
    public List<TaskDTO> searchTasks(@RequestParam String keyword) {
        logger.info("GET keyword/tasks called");
        return taskService.searchTasks(keyword);
    }

}
