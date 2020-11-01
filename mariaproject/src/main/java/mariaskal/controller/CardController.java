package mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mariaskal.model.Card;
import mariaskal.service.CardService;

@RestController
@RequestMapping("api/")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("cards")
    public List<Card> getCard() {
        return this.cardService.findAll();
    }
}