package com.hugbo.mariaskal.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.model.User;

@Service
public interface CardGroupService {
  CardGroup save(CardGroup cardGroup);

  void delete(CardGroup cardGroup);

  List<CardGroup> findAll();

  List<CardGroup> findByCreator(User user);
}