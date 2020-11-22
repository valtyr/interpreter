package com.hugbo.mariaskal.service.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.hugbo.mariaskal.service.GameService;
import com.hugbo.mariaskal.repository.GameRepository;
import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.model.Player;

@Service
public class GameImplementation implements GameService {

  GameRepository repository;

  @Autowired
  public GameImplementation(GameRepository gameRepository) {
    this.repository = gameRepository;
  }

  @Override
  public Game save(Game game) {
    return repository.save(game);
  }

  @Override
  public void delete(Game game) {
    repository.delete(game);
  }

  @Override
  public List<Game> findAll() {
    return repository.findAll();
  }

  @Override
  public List<Game> findByCreator(User creator) {
    return repository.findByCreator(creator);
  }

  @Override
  public Game findByShareId(String shareId) {
    return repository.findByShareId(shareId);
  }

  @Override
  public Game findById(long id) {
    return repository.findById(id);
  }
}