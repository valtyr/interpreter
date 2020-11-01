package mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import java.util.TreeSet;

@Entity
@Table(name = "cardgroups")
public class CardGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "card")
  private List<Card> cards;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User creator;

  @Column(name = "name")
  private String name;

  @Column(name = "creationDate")
  private LocalDateTime creationDate;

  @Column(name = "rating")
  private double rating;

  @Column(name = "timesUsed")
  private Long timesUsed;
  
  @Column(name = "tags")
  private TreeSet<String> tags;

  @Column(name = "numberOfRatings")
  private Long numberOfRatings;

  public CardGroup() {
  }

  public CardGroup(ArrayList<Card> cards, String name, User creator, LocalDateTime creationDate, double rating, Long timesUsed,
      TreeSet<String> tags) {
    super();
    this.cards = cards;
    this.name = name;
    this.creator = creator;
    this.creationDate = creationDate;
    this.rating = rating;
    this.numberOfRatings = (long)0;
    this.timesUsed = timesUsed;
    this.tags = tags;
  }

  public String toString(){
    return "Card Group:\nNo. Cards: "+cards.size()+"+\nCreator: "+creator.toString()+"\nCreation Date: "+creationDate.toString()+"\nRating: "+Double.toString(rating)+"\n No. Ratings: "+Long.toString(numberOfRatings)+"\nTimes Used: "+Long.toString(timesUsed);
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }


  public List<Card> getCards() {
    return this.cards;
  }

  public User getCreator() {
    return this.creator;
  }

  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

  public Long getTimesUsed() {
    return this.timesUsed;
  }
  
  public TreeSet<String> getTags() {
    return this.tags;
  }

  public void addCard(Card card) {
    this.cards.add(card);
  }

  public void removeCard(Card card) {
    this.cards.remove(card);
  }

  public void addTag(String tag) {
    this.tags.add(tag);
  }

  public double addRating(double rating) {
    this.numberOfRatings++;
    this.rating = (this.rating + rating) / this.numberOfRatings;
    return this.rating;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) { 
    this.name = name;
  }

}