package com.hugbo.mariaskal.service.implementation;

import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.service.CardGroupService;
import com.hugbo.mariaskal.repository.CardGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardGroupImplementation implements CardGroupService {

  CardGroupRepository repository;

  @Autowired
  public CardGroupImplementation(CardGroupRepository cardGroupRepository) {
    this.repository = cardGroupRepository;
  }

  @Override
  public CardGroup save(CardGroup cardGroup) {
    return repository.save(cardGroup);
  }

  @Override
  public void delete(CardGroup cardGroup) {
    repository.delete(cardGroup);
  }

  @Override
  public List<CardGroup> findAll() {
    return repository.findAll();
  }

  @Override
  public List<CardGroup> findByCreator(User user) {
    return repository.findByCreator(user);
  }
}