package koxanybak.springframework.cardgame.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    @CacheEvict(value = "card", allEntries = true) // TODO: not really necessary to evict all
    public List<Card> create(List<MultipartFile> images, List<String> deckNames) throws IOException {
        List<Deck> decksList = deckNames.stream()
            .map(deckName -> new Deck(deckName))
            .toList();
        
        List<Card> cards = images.stream().map(file -> {
            // Remove fileName extension
            String[] parts = file.getOriginalFilename().split("[.]");
            String cardName = String.join("", Arrays.copyOfRange(parts, 0, parts.length - 1));

            // Check for correct file type
            String fileType = parts[parts.length - 1];
            if (!fileType.equalsIgnoreCase("jpg")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong file type");
            }

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
