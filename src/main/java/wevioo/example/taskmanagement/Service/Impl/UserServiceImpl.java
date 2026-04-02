package wevioo.example.taskmanagement.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wevioo.example.taskmanagement.DTO.UserDTO;
import wevioo.example.taskmanagement.Mapper.UserMapper;
import wevioo.example.taskmanagement.Repository.UserRepository;
import wevioo.example.taskmanagement.Service.TaskService;
import wevioo.example.taskmanagement.Service.UserService;
import wevioo.example.taskmanagement.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Create new user");
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        //return mapToDTO(savedUser);
        logger.info("user created successfully");
        return userMapper.mapToDTO(savedUser);
    }

    @Override
    public Page<UserDTO> getUsersWithPagination(int page, int size, String sortBy) {
        logger.info("Fetching all users with pagination and sorting");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        logger.info("Number of users fetched successfully: {}", size);
        return userRepository.findAll(pageable)
                .map(userMapper::mapToDTO);
    }

    //    @Override
    //    public List<UserDTO> getAllUsers() {
    //        return userRepository.findAll()
    //                .stream()
    //                .map(this::mapToDTO)
    //                .toList();
    //    }

    @Override
    public UserDTO getUserById(Long id) {
        logger.info("Fetching user by ID");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //logger.debug("User with id {} not found", user.getId());
        logger.info("User with id {} not found", user.getId());
        logger.error("Error occurred while processing get user ID", new RuntimeException("user not found"));
        //return mapToDTO(user);
        logger.info("User ID {} is fetched successfully",user.getId());
        return userMapper.mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Delete user");
        userRepository.deleteById(id);
        logger.info("User deleted successfully with id {}",id);
    }

    @Override
    public Page<UserDTO> searchUsers(String name, String email, int page, int size) {
        logger.info("Search user");
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Number of users fetched successfully: {}", size);
        return userRepository.searchUsers(name, email, pageable)
                .map(userMapper::mapToDTO);
        //logger.debug("Number of users fetched: {}", tasks.size());
    }


//    // Mapper (Entity → DTO)
//    private UserDTO mapToDTO(User user) {
//        UserDTO dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        return dto;
//    }


}
