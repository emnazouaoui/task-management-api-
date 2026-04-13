package wevioo.example.taskmanagement.Mapper;

import org.springframework.stereotype.Component;
import wevioo.example.taskmanagement.DTO.UserDTO;
import wevioo.example.taskmanagement.Model.User;

@Component
public class UserMapper {

    // Mapper (Entity → DTO)
    public UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

}
