package mariaskal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mariaskal.model.Card;
import mariaskal.model.User;
@Service
public interface CardService {
    Card save(Card card);

    void delete(Card card);

    List<Card> findAll();
}