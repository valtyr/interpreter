package com.hugbo.mariaskal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hugbo.mariaskal.model.Card;
import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.model.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
  Game save(Game game);

  void delete(Game game);

  Game findById(long id);

  List<Game> findAll();

  List<Game> findByCreator(User creator);

  Game findByShareId(String shareId);
}