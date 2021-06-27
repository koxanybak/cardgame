package koxanybak.springframework.cardgame.repository.redis;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import koxanybak.springframework.cardgame.model.RoundRedis;

@Repository
public interface RoundRepository extends CrudRepository<RoundRedis, UUID> {
    
}
