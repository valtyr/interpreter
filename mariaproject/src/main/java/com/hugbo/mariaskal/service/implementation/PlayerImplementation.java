package com.hugbo.mariaskal.service.implementation;

import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.service.PlayerService;
import com.hugbo.mariaskal.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerImplementation implements PlayerService {

  PlayerRepository repository;

  @Autowired
  public PlayerImplementation(PlayerRepository playerRepository) {
    this.repository = playerRepository;
  }

  @Override
  public Player save(Player player) {
    return repository.save(player);
  }

  @Override
  public void delete(Player player) {
    repository.delete(player);
  }

  @Override
  public List<Player> findAll() {
    return repository.findAll();
  }

  @Override
  public List<Player> findByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public List<Player> findByUser(User user) {
    return repository.findByUser(user);
  }
}