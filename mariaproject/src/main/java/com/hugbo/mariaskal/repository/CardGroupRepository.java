package com.hugbo.mariaskal.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.model.User;

@Repository
public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
  CardGroup save(CardGroup card);

  void delete(CardGroup card);

  List<CardGroup> findAll();

  List<CardGroup> findByCreator(User creator);

  // @Query("select * from CardGroup")
  // List<CardGroup> findAllAndSort(Sort sort);
}