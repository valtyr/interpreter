package com.hugbo.mariaskal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.model.User;

@Service
public interface PlayerService {
  Player save(Player player);

  void delete(Player player);

  List<Player> findAll();

  List<Player> findByName(String name);

  List<Player> findByUser(User user);
}