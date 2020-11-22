package com.hugbo.mariaskal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hugbo.mariaskal.model.Card;
import com.hugbo.mariaskal.model.User;

@Service
public interface CardService {
    Card save(Card card);

    void delete(Card card);

    Card findById(long id);

    List<Card> findAll();
}