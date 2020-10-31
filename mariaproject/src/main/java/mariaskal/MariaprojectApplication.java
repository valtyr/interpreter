package mariaskal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import mariaskal.model.User;
import mariaskal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

@EnableJpaRepositories
@SpringBootApplication
public class MariaprojectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MariaprojectApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new User("Halli","Halli123", "hallib@gmail.com"));
		this.userRepository.save(new User("arni","arni123", "arni@gmail.com"));
		this.userRepository.save(new User("mani","mani123", "mani@gmail.com"));
	}

}
