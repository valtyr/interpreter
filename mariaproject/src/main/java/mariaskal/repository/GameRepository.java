package mariaskal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mariaskal.model.Card;
import mariaskal.model.Game;
import mariaskal.model.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
  Game save(Game game);
  void delete(Game game);
  List<Game> findAll();
  List<Game> findByCreator(User creator);
}