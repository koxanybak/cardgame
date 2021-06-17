package koxanybak.springframework.cardgame.dto.card;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CardGetAllDTO {
    private String cardName;
    private List<String> decks;
}
