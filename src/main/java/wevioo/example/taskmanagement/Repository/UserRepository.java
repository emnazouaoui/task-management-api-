package wevioo.example.taskmanagement.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wevioo.example.taskmanagement.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT u FROM User u
        WHERE (:name IS NULL OR u.name ILIKE CONCAT('%', :name, '%'))
        AND (:email IS NULL OR u.email ILIKE CONCAT('%', :email, '%'))
    """)
    Page<User> searchUsers(String name, String email, Pageable pageable);
}
