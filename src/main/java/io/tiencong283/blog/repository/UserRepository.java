package io.tiencong283.blog.repository;

import io.tiencong283.blog.model.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<WebUser, Integer> {
    Optional<WebUser> findByUserID(int userID); // Optional indicates the potential absence of a value

    WebUser findByUsername(String username);

    List<WebUser> findAll();

    boolean existsByUsername(String username);
}
