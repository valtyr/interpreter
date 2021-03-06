package com.hugbo.mariaskal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.model.User;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
  Player save(Player player);

  void delete(Player player);

  Player findByConnectionId(UUID connectionId);

  List<Player> findAll();

  List<Player> findByName(String name);

  List<Player> findByUser(User user);
}