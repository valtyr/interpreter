package mariaskal.service.implementation;

import mariaskal.model.User;
import mariaskal.service.UserService;
import mariaskal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImplementation implements UserService {
  
  UserRepository repository;

  @Autowired
  public UserImplementation(UserRepository userRepository) {
    this.repository = userRepository;
  }

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public void delete(User user) {
    repository.delete(user);
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

   @Override
   public Optional<User> findByEmail(String email) {
     return repository.findByEmail(email);
   }
  
  @Override
  public List<User> findByName(String name) {
      return repository.findByName(name);
  }
}