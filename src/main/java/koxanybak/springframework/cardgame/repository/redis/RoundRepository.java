package koxanybak.springframework.cardgame.repository.redis;

import java.util.UUID;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import koxanybak.springframework.cardgame.model.RoundRedis;

@Repository
public interface RoundRepository extends KeyValueRepository<RoundRedis, UUID> {
    
}
