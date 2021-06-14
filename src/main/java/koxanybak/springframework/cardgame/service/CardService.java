package koxanybak.springframework.cardgame.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.model.Deck;
import koxanybak.springframework.cardgame.repository.jpa.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    public List<Card> create(MultipartFile[] images, List<String> deckNames) throws IOException {
        // TODO file type check
        List<Deck> decksList = deckNames.stream()
            .map(deckName -> new Deck(deckName))
            .toList();
        
        List<Card> cards = Stream.of(images).map(file -> {
            // Remove fileName extension
            String[] parts = file.getOriginalFilename().split("[.]");
            String cardName = String.join("", Arrays.copyOfRange(parts, 0, parts.length - 1));

            try {
                return new Card(cardName, decksList, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        List<Card> newCards = cardRepository.saveAll(cards);
        cardRepository.flush();

        return newCards;
    }

    public Card findOne(String cardName) {
        // TODO Lazy loading
        Optional<Card> cardInDb = cardRepository.findByName(cardName);
        if (cardInDb.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return cardInDb.get();
    }

    public List<Card> findAll(String deckName) {
        return cardRepository.findAllByDeckName(deckName);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }
}
