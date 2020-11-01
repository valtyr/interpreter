package mariaskal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mariaskal.model.CardGroup;
import mariaskal.model.User;

@Service
public interface CardGroupService {
  CardGroup save(CardGroup cardGroup);

  void delete(CardGroup cardGroup);

  List<CardGroup> findAll();

  List<CardGroup> findByCreator(User user);
}