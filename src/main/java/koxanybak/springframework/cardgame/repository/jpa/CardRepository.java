package koxanybak.springframework.cardgame.repository.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import koxanybak.springframework.cardgame.model.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    Optional<Card> findByFileName(String fileName);
}
