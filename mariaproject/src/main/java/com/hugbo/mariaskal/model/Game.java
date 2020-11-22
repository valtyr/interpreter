package com.hugbo.mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hugbo.mariaskal.helpers.RandomID;
import com.hugbo.mariaskal.service.CardGroupService;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @JsonIgnore
  @Column(name = "elapsedTime")
  private Long elapsedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private Player creator;

  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "playerList")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private List<Player> playerList;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "playerWinner")
  private Player winner;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "currentPlayer")
  private Player currentPlayer;

  @Column(name = "currentRound")
  private Integer currentRound;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "currentCard")
  private Card currentCard;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cardGroup")
  private CardGroup cardGroup;

  @Column(name = "shareId", unique = true)
  private String shareId;

  // @Transient
  // private Player explainer;

  // @Transient
  // private Player guesser;

  public Game() {
    this.shareId = RandomID.generate();
  }

  public Game(Player creator) {
    this.shareId = RandomID.generate();
    this.creator = creator;
    this.playerList = new ArrayList<Player>();
    this.playerList.add(creator);
    this.cardGroup = new CardGroup();
  }

  public Game(Player creator, List<Player> playerList, CardGroup cardGroup) {
    this.creator = creator;
    this.playerList = playerList;
    this.cardGroup = cardGroup;
  }

  public void shufflePlayers() {
    Collections.shuffle(this.playerList);
  }

  /*
   * public Player nextPlayer() {
   * 
   * 
   * }
   */

  public String getShareId() {
    return this.shareId;
  }

  public long getId() {
    return id;
  }

  // public Player getWinner(

  public void addPlayer(Player player) {
    playerList.add(player);
  }

  public void setWinner(Player winner) {
    this.winner = winner;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public Integer getCurrentRound() {
    return currentRound;
  }

  public void setCurrentRound(Integer currentRound) {
    this.currentRound = currentRound;
  }

  public Card getCurrentCard() {
    return currentCard;
  }

  public void setCurrentCard(Card currentCard) {
    this.currentCard = currentCard;
  }

  public CardGroup getCardGroup() {
    return cardGroup;
  }

  public void setCardGroup(CardGroup cardGroup) {
    this.cardGroup = cardGroup;
  }

  public List<Player> getPlayerList() {
    return this.playerList;
  }

}