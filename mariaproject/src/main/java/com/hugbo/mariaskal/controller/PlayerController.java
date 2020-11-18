
package com.hugbo.mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.service.PlayerService;

@RestController

@RequestMapping("api/")
public class PlayerController {

  @Autowired
  private PlayerService playerService;

  @GetMapping("players")
  public List<Player> getPlayer() {
    return this.playerService.findAll();
  }
}
