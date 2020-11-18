package com.hugbo.mariaskal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.hugbo.mariaskal.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User User);

    void delete(User User);

    List<User> findAll();

    List<User> findByName(String name);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
}