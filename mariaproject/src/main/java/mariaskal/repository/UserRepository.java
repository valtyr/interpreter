package mariaskal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mariaskal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
}