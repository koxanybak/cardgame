package koxanybak.springframework.cardgame.dto.round;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RoundGetOneDTO {
    private UUID id;
    private List<String> cardNames;
}
