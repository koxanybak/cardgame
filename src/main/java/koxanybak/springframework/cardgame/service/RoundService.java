package koxanybak.springframework.cardgame.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.model.RoundRedis;
import koxanybak.springframework.cardgame.repository.redis.RoundRepository;

@Service
public class RoundService {
    
    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private CardService cardService;

    public RoundRedis create(String deckName) {
        // TODO optimize query
        List<Card> cardsInDeck = cardService.findAll(deckName);
        shuffleCards(cardsInDeck);
        
        List<String> cardNames = cardsInDeck.stream().map(Card::getName).toList();
        RoundRedis round = new RoundRedis(UUID.randomUUID(), 0, cardNames);

        return roundRepository.save(round);
    }

    public RoundRedis getRound(UUID roundId) {
        // TODO optimize queries
        Optional<RoundRedis> roundInDb = roundRepository.findById(roundId);
        if (roundInDb.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return roundInDb.get();
    }

    public byte[] getNextCard(UUID roundId) {
        // Fetch card
        RoundRedis existingRound = getRound(roundId);
        String cardName = existingRound.getCardNames().get(existingRound.getNextCard());
        Card cardInDb = cardService.findOne(cardName);

        // Remove round if over
        existingRound.increaseNextCard();
        if (existingRound.isOver()) {
            roundRepository.delete(existingRound);
        } else {
            roundRepository.save(existingRound);
        }

        return cardInDb.getImage();
    }

    private void shuffleCards(List<Card> cards) {
        Collections.shuffle(cards);
    }
}
