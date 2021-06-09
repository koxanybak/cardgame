package koxanybak.springframework.cardgame.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash(value = "round")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundRedis implements Serializable {
    @Id
    private UUID id;

    private Integer nextCard;
}
