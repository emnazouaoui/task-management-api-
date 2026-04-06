package wevioo.example.taskmanagement.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import wevioo.example.taskmanagement.DTO.UserDTO;
import wevioo.example.taskmanagement.Service.UserService;


@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "CRUD operations for users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    //  Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user
    @PostMapping
    @Operation(summary = "Create user", description = "Create user")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("POST /users called");
        return userService.createUser(userDTO);
    }

    // Get all users
    @GetMapping
    @Operation(summary = "Get all user", description = "Get all tasks with sorting and pagination")
    public Page<UserDTO> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        logger.info("GET /users called");
        return userService.getUsersWithPagination(page, size,sortBy);
    }


    // Get user by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user by ID")
    public UserDTO getUserById(@PathVariable Long id) {
        logger.info("GET user/users called");
        return userService.getUserById(id);
    }

    // Delete user
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete user by ID")
    public void deleteUser(@PathVariable Long id) {
        logger.info("DELETE /users called");
        userService.deleteUser(id);
    }


    // Search users by name or email
    @GetMapping("/search")
    @Operation(summary = "Search user", description = "Get users with filter by name and email pagination and sorting")
    public Page<UserDTO> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        logger.info("GET user search /users called");
        return userService.searchUsers(name, email, page, size);
    }

}
