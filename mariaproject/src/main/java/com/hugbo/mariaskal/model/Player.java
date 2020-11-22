package com.hugbo.mariaskal.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;

import java.util.UUID;

@Entity
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "connectionId")
  private UUID connectionId;

  @Column(name = "name")
  private String name;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  private User user;

  @Column(name = "score")
  private int score;

  public Player() {

  }

  public Player(String name, UUID uuid) {
    this.name = name;
    this.connectionId = uuid;
  }

  public Player(String name, UUID uuid, User user) {
    this.name = name;
    this.connectionId = uuid;
    this.user = user;
    this.score = 0;
  }

  public UUID getConnectionId() {
    return this.connectionId;
  }

  public long getId() {
    return this.id;
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