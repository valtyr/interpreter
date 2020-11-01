package mariaskal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//import javax.validation.Valid;

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


    @GetMapping("cardGroups")
    public List<CardGroup> getCardGroups() {
        return this.cardGroupService.findAll();
    }

    @PostMapping("addCardGroup")
    ResponseEntity<CardGroup> createGroup(@Validated @RequestBody String name) throws URISyntaxException {
        CardGroup group = new CardGroup(name);
        CardGroup result = this.cardGroupService.save(group);
        return ResponseEntity.created(new URI("cardGroups/" + result.getId()))
                .body(result);
    }

    

    // @GetMapping("cardGroup")
    // public List<CardGroup> getCardGroup() {}


}
    

    
