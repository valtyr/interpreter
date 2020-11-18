package com.hugbo.mariaskal.model;

import com.hugbo.mariaskal.model.CardGroup;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "cards")
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "word")
  private String word;

  // @Column(name = "creator")
  // private Player player;

  @ManyToMany(cascade = { CascadeType.REFRESH }, mappedBy = "cards")
  List<CardGroup> cardGroups = new ArrayList<CardGroup>();

  public Card() {

  }

  public Card(String word) {
    this.word = word;
  }

  public long getId() {
    return this.id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void addCardGroup(CardGroup cardGroup) {
    this.cardGroups.add(cardGroup);
  }
}