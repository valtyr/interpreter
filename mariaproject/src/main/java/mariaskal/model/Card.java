package mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "cards")
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(name = "word")
  private String word;

  public Card(String word) {
    this.word = word;
  }
  public long getCardid() {
    return this.id;
  }
}