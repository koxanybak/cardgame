package koxanybak.springframework.cardgame.util.mapping;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;

import koxanybak.springframework.cardgame.model.Card;

public class CardListStringConverter extends AbstractConverter<List<Card>, List<String>> {
    
    @Override
    protected List<String> convert(List<Card> cards) {
        return cards.stream()
            .map(Card::getName)
            .collect(Collectors.toList());
    }
}
