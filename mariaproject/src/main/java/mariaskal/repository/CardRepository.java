package mariaskal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mariaskal.model.Card;
import mariaskal.model.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
  Card save(Card card);
  void delete(Card card);
  List<Card> findAll();
}