package koxanybak.springframework.cardgame;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import koxanybak.springframework.cardgame.dto.card.CardGetAllDTO;
import koxanybak.springframework.cardgame.dto.card.CardGetOneDTO;
import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.util.mapping.DeckListStringConverter;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "koxanybak.springframework.cardgame.repository.jpa")
@EnableCaching
public class CardgameApplication {

	@Bean
	// TODO Refactor elsewhere
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.typeMap(Card.class, CardGetOneDTO.class).addMappings(mapper -> 
			mapper.using(new DeckListStringConverter())
				.map(Card::getDecks, CardGetOneDTO::setDecks)
		);
		modelMapper.typeMap(Card.class, CardGetAllDTO.class).addMappings(mapper -> 
			mapper.using(new DeckListStringConverter())
				.map(Card::getDecks, CardGetAllDTO::setDecks)
		);

		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardgameApplication.class, args);
	}

}
