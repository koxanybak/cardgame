package koxanybak.springframework.cardgame.util.mapping;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;

import koxanybak.springframework.cardgame.model.Deck;

public class DeckListConverter extends AbstractConverter<List<Deck>, List<String>>{

    @Override
    protected List<String> convert(List<Deck> decks) {
        return decks.stream()
            .map(Deck::getName)
            .collect(Collectors.toList());
    }
}
