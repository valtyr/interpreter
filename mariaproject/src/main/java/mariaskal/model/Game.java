package mariaskal.model;

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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "elapsedTime")
  private Long elapsedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private User creator;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "playerList")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cardGroup")
  private CardGroup cardGroup;

  public Game() {

  }
  
  public Game(User creator, List<Player> playerList, CardGroup cardGroup) {
    super();
    this.creator = creator;
    this.playerList = playerList;
    this.cardGroup = cardGroup;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getElapsedTime() {
    return elapsedTime;
  }

  public void setElapsedTime(Long elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public List<Player> getPlayerList() {
    return playerList;
  }

  public void setPlayerList(List<Player> playerList) {
    this.playerList = playerList;
  }

  public Player getWinner() {
    return winner;
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

  
  
  


}