package koxanybak.springframework.cardgame.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import koxanybak.springframework.cardgame.model.RoundRedis;
import koxanybak.springframework.cardgame.repository.redis.RoundRepository;

@Service
public class RoundService {
    
    @Autowired
    private RoundRepository roundRepository;

    public RoundRedis create() {
        RoundRedis round = new RoundRedis(UUID.randomUUID(), 1);
        return roundRepository.save(round);
    }
}
