package hw6.repository;

import hw6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "UPDATE User u SET u.username = :username WHERE u.id = :id")
    void updateUsernameById(@Param("username") String username, @Param("id") Long id);
}
