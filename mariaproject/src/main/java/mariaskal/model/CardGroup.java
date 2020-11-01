package mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDateTime;


@Entity
@Table(name = "cardgroups")
public class CardGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  //@ManyToMany(cascade = {CascadeType.ALL})
  //@JoinColumn(name = "cardgroups_id")
  @ManyToMany(cascade=CascadeType.REFRESH)
  @JoinTable(
    name = "card_group_mapping",
    joinColumns = @JoinColumn(name = "cardgroups_id"),
    inverseJoinColumns = @JoinColumn(name = "cards_id"))
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
  @ElementCollection(targetClass=String.class)
  private Set<String> tags;

  @Column(name = "numberOfRatings")
  private Long numberOfRatings;

  public CardGroup() {
  }

  public CardGroup(ArrayList<Card> cards, String name, User creator, Set<String> tags) {
    super();
    this.cards = cards;
    this.name = name;
    this.creator = creator;
    this.creationDate = LocalDateTime.now();
    this.rating = 3.5;
    this.numberOfRatings = (long)0;
    this.timesUsed = (long)0;
    this.tags = tags;
  }

  public CardGroup(String name) {
    this.name = name;
    this.creationDate = LocalDateTime.now();
    this.cards = new ArrayList<Card>();
    this.rating = 3.5;
    this.numberOfRatings = (long)0;
    this.timesUsed = (long)0;
    this.tags = new TreeSet<String>();
    User defaultUser = new User("Jon Jonsson", "jj@jj.com");
    this.creator = defaultUser;
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
  
  public Set<String> getTags() {
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

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

}