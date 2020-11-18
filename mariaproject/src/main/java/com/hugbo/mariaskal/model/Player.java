package com.hugbo.mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.util.UUID;

@Entity
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "connectionID")
  private UUID connectionID;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  private User user;

  @Column(name = "score")
  private int score;

  /*
   * @OneToMany
   * 
   * @JoinColumn(name = "players.id") private List<Card> cards;
   */

  public Player() {

  }

  public Player(String name, UUID uuid) {
    this.name = name;
    this.connectionID = uuid;
  }

  public Player(String name, UUID uuid, User user) {
    this.name = name;
    this.connectionID = uuid;
    this.user = user;
    this.score = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getScore() {
    return this.score;
  }

  public void addPoint() {
    ++this.score;
  }
}