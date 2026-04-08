package wevioo.example.taskmanagement.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    @Pattern(regexp = "TODO|DOING|DONE", message = "Status must be TODO, DOING or DONE")
    private String status;

    private Long assignedUserId;



}
