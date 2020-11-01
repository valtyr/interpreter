package mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mariaskal.model.CardGroup;
import mariaskal.service.CardGroupService;
import mariaskal.repository.CardGroupRepository;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class CardGroupController {

    @Autowired
    private CardGroupService cardGroupService;

    @Autowired
    private CardGroupRepository cardGroupRepository;

    /*
    @Autowired
    public CardGroupController(CardGroupService cardGroupService) {
        this.cardGroupService = cardGroupService;
    }
    */

    //@RequestMapping(value = "/cardGroups", method = RequestMethod.GET)
    @GetMapping("cardGroups")
    public List<CardGroup> getCardGroups() {
        return this.cardGroupService.findAll();
    }

    // @GetMapping("cardGroup")
    // public List<CardGroup> getCardGroup() {}


}
    

    
