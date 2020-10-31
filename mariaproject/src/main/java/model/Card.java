package model;

import javax.persitence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
public class Card {
  @Id
  @GenertedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(name = "word")
  private String word;

  public Card(String word) {
    this.word = word;
  }
  public Card getCard() {
    return Card;
  }
}