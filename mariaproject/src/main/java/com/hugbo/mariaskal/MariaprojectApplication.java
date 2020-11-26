package com.hugbo.mariaskal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hugbo.mariaskal.model.Card;
import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.model.User;
import com.hugbo.mariaskal.service.CardGroupService;
import com.hugbo.mariaskal.repository.CardGroupRepository;
import com.hugbo.mariaskal.repository.CardRepository;
import com.hugbo.mariaskal.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.beans.factory.annotation.Autowired;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EnableJpaRepositories
@SpringBootApplication
@CrossOrigin(origins = "*")
public class MariaprojectApplication implements CommandLineRunner {

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

	}

}
