package com.hugbo.mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.repository.UserRepository;
import com.hugbo.mariaskal.service.UserService;

@RestController
@RequestMapping("api/")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("users")
  public List<User> getUsers() {
    return this.userService.findAll();
  }
}