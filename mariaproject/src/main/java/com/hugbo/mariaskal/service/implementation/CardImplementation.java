package com.hugbo.mariaskal.service.implementation;

import com.hugbo.mariaskal.model.Card;
import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.service.CardGroupService;
import com.hugbo.mariaskal.service.CardService;
import com.hugbo.mariaskal.repository.CardGroupRepository;
import com.hugbo.mariaskal.repository.CardRepository;

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