package com.hugbo.mariaskal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hugbo.mariaskal.model.User;
import java.util.Optional;

@Service
public interface UserService {
    User save(User User);

    void delete(User User);

    List<User> findAll();

    List<User> findByName(String name);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}