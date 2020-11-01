package mariaskal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import mariaskal.model.Card;
import mariaskal.model.CardGroup;
import mariaskal.model.User;
import mariaskal.service.CardGroupService;
import mariaskal.repository.CardGroupRepository;
import mariaskal.repository.CardRepository;
import mariaskal.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.beans.factory.annotation.Autowired;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@EnableJpaRepositories
@SpringBootApplication
@CrossOrigin(origins="*")
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
		User halli = new User("Halli Dabbi", "hallib@gmail.com");
		User arni = new User("arni litli", "arni@gmail.com");
		User valtyr = new User("valtyr", "valtyr@gmail.com");
		User mani = new User("arni abi", "arni@gmail.com");
		this.userRepository.save(halli);
		this.userRepository.save(arni);
		this.userRepository.save(valtyr);
		this.userRepository.save(mani);


		//card
		Card card1 = new Card("maniapi");
		Card card2 = new Card("maniapi2");
		this.cardRepository.save(card1);
		this.cardRepository.save(card2);

		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(card1);
		cards.add(card2);


		//treeset
		Set<String> tags = new TreeSet<String>();
		tags.add("banjo");
		CardGroup g = new CardGroup(cards,"Hallaspil",halli, tags);
		// card1.addCardGroup(g);
		// card2.addCardGroup(g);
		CardGroup b = new CardGroup(cards,"Vallaspil",valtyr, tags);
		// card1.addCardGroup(b);
		// card2.addCardGroup(b);

		CardGroup c = new CardGroup(cards,"Vallaspil",arni, tags);

		//cardGroup
		this.cardGroupService.save(g);
		this.cardGroupService.save(b);
		this.cardGroupService.save(c);
		//this.cardGroupService.save(g);
	}

}
