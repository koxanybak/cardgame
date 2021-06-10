package koxanybak.springframework.cardgame.dto.card;

import java.util.List;

import lombok.Data;

@Data
public class CardGetOneDTO {
    private String cardName;
    private List<String> decks;
}
