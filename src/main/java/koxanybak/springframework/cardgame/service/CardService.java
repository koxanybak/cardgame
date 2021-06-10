package koxanybak.springframework.cardgame.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.model.Deck;
import koxanybak.springframework.cardgame.repository.jpa.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    public Iterable<Card> create(MultipartFile[] images, List<String> deckNames) throws IOException {
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

        cards.stream().forEach(im -> {
            System.out.println(im.getCardName());
            System.out.println(im.getImage().length);
        });
        return cardRepository.saveAll(cards);
    }

    public Optional<Card> findOne(String cardName) {
        // TODO Lazy loading
        return cardRepository.findByCardName(cardName);
    }
}
