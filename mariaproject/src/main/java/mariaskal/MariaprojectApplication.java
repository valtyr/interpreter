package mariaskal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import mariaskal.model.Card;
import mariaskal.model.CardGroup;
import mariaskal.model.User;
import mariaskal.service.CardGroupService;
import mariaskal.repository.CardGroupRepository;
import mariaskal.repository.CardRepository;
import mariaskal.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.beans.factory.annotation.Autowired;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@EnableJpaRepositories
@SpringBootApplication
public class MariaprojectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MariaprojectApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardGroupRepository cardGroupRepostiory;

	@Autowired
	private CardGroupService cardGroupService;

	@Override
	public void run(String... args) throws Exception {
		
		//user
		User halli = new User("Halli","Halli123", "hallib@gmail.com");
		this.userRepository.save(halli);
		this.userRepository.save(new User("arni","arni123", "arni@gmail.com"));
		this.userRepository.save(new User("mani","mani123", "mani@gmail.com"));

		//card
		this.cardRepository.save(new Card("maniapi"));
		this.cardRepository.save(new Card("maniapi2"));
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card("maniapi3"));
		cards.add(new Card("maniapi4"));


		//treeset
		TreeSet<String> tags = new TreeSet<String>();
		tags.add("banjo");
		CardGroup g = new CardGroup(cards, halli, LocalDateTime.now(), 0.0, (long)1, tags);

		//cardGroup
		this.cardGroupRepostiory.save(g);
		//this.cardGroupService.save(g);
	}

}
