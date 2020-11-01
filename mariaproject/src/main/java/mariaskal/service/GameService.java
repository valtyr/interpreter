package mariaskal.service;

import java.util.List;

import mariaskal.model.Game;
import mariaskal.model.User;

public interface GameService {
    Game save(Game game);

    void delete(Game game);

    List<Game> findAll();

    List<Game> findByCreator(User creator);
}