package wevioo.example.taskmanagement.Mapper;

import org.springframework.stereotype.Component;
import wevioo.example.taskmanagement.DTO.TaskDTO;
import wevioo.example.taskmanagement.entity.Task;

import java.util.List;

@Component
public class TaskMapper {

    // Mapper (Entity → DTO)
    public TaskDTO toDTO(Task task) {
        if (task == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        if (task.getAssignedUser() != null) {
            dto.setAssignedUserId(task.getAssignedUser().getId());
        }

        return dto;
    }


//    public Task toEntity(TaskDTO dto) {
//        if (dto == null) return null;
//        Task task = new Task();
//        task.setId(dto.getId());
//        task.setTitle(dto.getTitle());
//        task.setDescription(dto.getDescription());
//        task.setStatus(dto.getStatus());
//        if (dto.getAssignedUserId() != null) {
//            task.setAssignedUser(dto.getAssignedUserId().getId());
//        }
//
//        return task;
//    }

    public List<TaskDTO> toDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDTO)
                .toList();
    }

}
