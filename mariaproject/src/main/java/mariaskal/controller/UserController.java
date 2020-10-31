package mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mariaskal.model.User;
import mariaskal.repository.UserRepository;



@RestController
@RequestMapping("api/")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("users")
  public List<User> getUsers() {
    return this.userRepository.findAll();
  }
}