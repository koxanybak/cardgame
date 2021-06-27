package koxanybak.springframework.cardgame.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash(value = "round", timeToLive = 60*60*24)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundRedis implements Serializable {

    @Id
    private UUID id;
    private Integer nextCard;
    private List<String> cardNames;

    public void increaseNextCard() {
        this.nextCard++;
    }

    public boolean isOver() {
        return this.nextCard >= this.cardNames.size();
    }
}
