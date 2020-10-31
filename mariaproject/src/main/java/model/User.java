package model;

import javax.persitence.Entity;
import javax.persistence.Table;
import javax.persistance.Id;
import javax.persistance.GeneratedValue;
import javax.persistance.GenerationType;

@Entity
@Table(name = "cards")
public class Card {
  @Id
  @GenertedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String email;

  public User(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
  public User getUser() {
    return User;
  }
}