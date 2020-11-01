package mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mariaskal.model.Game;
import mariaskal.repository.GameRepository;
import mariaskal.service.GameService;

@RestController
@RequestMapping("api/")
public class GameController {
    @Autowired
    private GameService gameRepository;

    @GetMapping("games")
    public List<Game> getGame() {
        return this.gameRepository.findAll();
    }


}