package com.hugbo.mariaskal.model;

import com.hugbo.mariaskal.model.CardGroup;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private Player player;

  public Card() {

  }

  public Card(String word) {
    this.word = word;
  }

  public Card(String word, Player player) {
    this.word = word;
    this.player = player;
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

  public UUID getCreatorId() {
    if (player == null) {
      return null;
    }
    return this.player.getConnectionId();
  }
}