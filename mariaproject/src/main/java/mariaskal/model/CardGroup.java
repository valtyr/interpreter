package mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.ArrayList;

import java.time.LocalDateTime;

@Entity
@Table(name = "cardgroups")
public class CardGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "card")
  private ArrayList<Card> cards;

  @Column(name = "creatorid")
  private Long creatorID;

  @Column(name = "creationDate")
  private LocalDateTime creationDate;

  @Column(name = "rating")
  private double rating;

  @Column(name = "timesUsed")
  private Long timesUsed;
  
  @Column(name = "tags")
  private ArrayList<String> tags;

  public CardGroup(ArrayList<Card> cards, Long creatorId, LocalDateTime creationDate, double rating, Long timesUsed,
      ArrayList<String> tags) {
    this.cards = cards;
    this.creatorID = creatorId;
    this.creationDate = creationDate;
    this.rating = rating;
    this.timesUsed = timesUsed;
    this.tags = tags;
  }

}