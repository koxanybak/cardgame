package koxanybak.springframework.cardgame.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import koxanybak.springframework.cardgame.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    @Query(
        "SELECT c FROM Card c "
        + "INNER JOIN c.decks d WHERE d.name = :deckName"
    )
    List<Card> findAllByDeckName(@Param("deckName") String deckName);

    @Cacheable("card")
    Optional<Card> findByName(String name);
}
