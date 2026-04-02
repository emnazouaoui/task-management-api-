package wevioo.example.taskmanagement.Service;

import org.springframework.data.domain.Page;
import wevioo.example.taskmanagement.DTO.TaskDTO;
import wevioo.example.taskmanagement.DTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    //List<UserDTO> getAllUsers();
    Page<UserDTO> getUsersWithPagination(int page, int size, String sortBy);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    Page<UserDTO> searchUsers(String name, String email, int page, int size);
}
