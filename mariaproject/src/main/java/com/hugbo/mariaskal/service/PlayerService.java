package com.hugbo.mariaskal.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.model.User;

@Service
public interface PlayerService {
  Player save(Player player);

  void delete(Player player);

  Player findByConnectionId(UUID connectionId);

  Player createPlayerFromConnectionId(UUID connectionId, String username);

  List<Player> findAll();

  List<Player> findByName(String name);

  Player findById(Long id);

  List<Player> findByUser(User user);
}