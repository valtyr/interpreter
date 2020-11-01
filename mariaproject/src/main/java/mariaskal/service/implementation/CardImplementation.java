package mariaskal.service.implementation;

import mariaskal.model.Card;
import mariaskal.model.CardGroup;
import mariaskal.model.User;
import mariaskal.service.CardGroupService;
import mariaskal.service.CardService;
import mariaskal.repository.CardGroupRepository;
import mariaskal.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardImplementation implements CardService {
  
  CardRepository repository;

  @Autowired
  public CardImplementation(CardRepository cardRepository) {
    this.repository = cardRepository;
  }

  @Override
  public Card save(Card card) {
    return repository.save(card);
  }

  @Override
  public void delete(Card card) {
    repository.delete(card);
  }

  @Override
  public List<Card> findAll() {
    return repository.findAll();
  }
}