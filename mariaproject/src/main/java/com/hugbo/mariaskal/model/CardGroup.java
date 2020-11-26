package com.hugbo.mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hugbo.mariaskal.helpers.ISOTimestamp;

import java.util.LinkedHashSet;

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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.TreeSet;
import java.time.LocalDateTime;

@Entity
@Table(name = "cardgroups")
public class CardGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // @ManyToMany(cascade = {CascadeType.ALL})
  // @JoinColumn(name = "cardgroups_id")
  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "cards")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Set<Card> cards;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Player creator;

  @Column(name = "name")
  private String name;

  @Column(name = "creationDate")
  private String creationDate;

  @Column(name = "rating")
  private double rating;

  @Column(name = "timesUsed")
  private Long timesUsed;

  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private Set<String> tags;

  @Column(name = "numberOfRatings")
  private Long numberOfRatings;

  @JsonInclude
  @Column(name = "published")
  private boolean published;

  public CardGroup() {
    this.cards = new LinkedHashSet<Card>();
    this.timesUsed = (long) 0;
  }

  public CardGroup(LinkedHashSet<Card> cards, String name, Player creator, Set<String> tags) {
    this.cards = cards;
    this.name = name;
    this.creator = creator;
    this.creationDate = ISOTimestamp.getISOTimestamp();
    this.rating = 3.5;
    this.numberOfRatings = (long) 0;
    this.timesUsed = (long) 0;
    this.tags = tags;
    this.published = false;
  }

  public CardGroup(String name) {
    this.name = name;
    this.creationDate = ISOTimestamp.getISOTimestamp();
    this.cards = new LinkedHashSet<Card>();
    this.rating = 0;
    this.numberOfRatings = (long) 0;
    this.timesUsed = (long) 0;
    this.tags = new TreeSet<String>();
    this.published = false;
    // User defaultUser = new User("Jon Jonsson", "jj@jj.com");
    // this.creator = defaultUser;
  }

  public void shuffleCards() {
    ArrayList<Card> cardList = new ArrayList<Card>();
    cardList.addAll(this.cards);
    Collections.shuffle(cardList);
    this.cards = new LinkedHashSet<Card>(cardList);
  }

  public String toString() {
    return "Card Group:\nNo. Cards: " + cards.size() + "+\nCreator: " + creator.toString() + "\nCreation Date: "
        + creationDate.toString() + "\nRating: " + Double.toString(rating) + "\n No. Ratings: "
        + Long.toString(numberOfRatings) + "\nTimes Used: " + Long.toString(timesUsed);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Set<Card> getCards() {
    return this.cards;
  }

  public void setDateTime(String creationDate) {
    this.creationDate = creationDate;
  }

  public Player getCreator() {
    return this.creator;
  }

  public String getCreationDate() {
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
    this.rating = (this.rating * (this.numberOfRatings - 1) + rating) / this.numberOfRatings;
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

  public void setPublished(boolean published) {
    this.published = published;
  }

  public boolean getPublished() {
    return this.published;
  }

  public void setTags(LinkedHashSet<String> tags) {
    this.tags = tags;
  }

  public void setCreator(Player creator) {
    this.creator = creator;
  }

  public void incrementTimesUsed() {
    this.timesUsed++;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

}