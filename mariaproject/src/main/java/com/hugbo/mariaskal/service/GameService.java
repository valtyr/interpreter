package com.hugbo.mariaskal.service;

import java.util.List;

import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.model.User;

public interface GameService {
    Game save(Game game);

    void delete(Game game);

    List<Game> findAll();

    List<Game> findByCreator(User creator);

    Game findByShareId(String shareId);

    Game findById(long id);

}