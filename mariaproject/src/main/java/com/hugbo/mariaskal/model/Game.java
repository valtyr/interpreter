package com.hugbo.mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

  @Column(name = "gameStartedTime")
  private String gameStartedTime;

  @Column(name = "turnStartedTime")
  private String turnStartedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Player creator;

  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "playerList")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Set<Player> playerList;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "playerWinner")
  private Player winner;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "currentPlayer")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Player currentPlayer;

  @Column(name = "currentPlayerIdx")
  private int currentPlayerIdx;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "currentGuesser")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Player currentGuesser;

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

  @Column(name = "playerSequence")
  private ArrayList<Long> playerSequence;

  @Column(name = "cardSequence")
  @Lob
  private ArrayList<Long> cardSequence;

  @Column(name = "curentCardIdx")
  private int currentCardIdx;

  @Column(name = "turnInProgress")
  private boolean turnInProgress;

  @Column(name = "gameOver")
  private boolean gameOver;

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
    this.playerList = new HashSet<Player>();
    this.playerList.add(creator);
    this.cardGroup = new CardGroup();
  }

  public Game(Player creator, HashSet<Player> playerList, CardGroup cardGroup) {
    this.creator = creator;
    this.playerList = playerList;
    this.cardGroup = cardGroup;
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

  public Player getCurrentGuesser() {
    return currentGuesser;
  }

  public void setCurrentGuesser(Player currentGuesser) {
    this.currentGuesser = currentGuesser;
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

  public Set<Player> getPlayerList() {
    return this.playerList;
  }

  public Player getCreator() {
    return this.creator;
  }

  public ArrayList<Long> getPlayerSequence() {
    return this.playerSequence;
  }

  public void setPlayerSequence(ArrayList<Long> sequence) {
    this.playerSequence = sequence;
  }

  public int getCurrentPlayerIdx() {
    return this.currentPlayerIdx;
  }

  public void setCurrentPlayerIdx(int idx) {
    this.currentPlayerIdx = idx;
  }

  public ArrayList<Long> getCardSequence() {
    return this.cardSequence;
  }

  public void setCardSequence(ArrayList<Long> sequence) {
    this.cardSequence = sequence;
  }

  public int getCurrentCardIdx() {
    return this.currentCardIdx;
  }

  public void setCurrentCardIdx(int idx) {
    this.currentCardIdx = idx;
  }

  public boolean getTurnInProgress() {
    return this.turnInProgress;
  }

  public void setTurnInProgress(boolean turnInProgress) {
    this.turnInProgress = turnInProgress;
  }

  public void setGameStartedTime(String gameStartedTime) {
    this.gameStartedTime = gameStartedTime;
  }

  public String getGameStartedTime() {
    return this.gameStartedTime;
  }

  public void setTurnStartedTime(String turnStartedTime) {
    this.turnStartedTime = turnStartedTime;
  }

  public String getTurnStartedTime() {
    return this.turnStartedTime;
  }

  public boolean getGameOver() {
    return this.gameOver;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

}