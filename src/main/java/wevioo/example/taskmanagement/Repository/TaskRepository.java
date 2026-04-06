package wevioo.example.taskmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wevioo.example.taskmanagement.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
        SELECT * FROM task t 
        WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(t.status) LIKE LOWER(CONCAT('%', :keyword, '%'))
        
    """, nativeQuery = true)

    List<Task> searchTasks(@Param("keyword") String keyword);

}
