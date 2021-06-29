package koxanybak.springframework.cardgame.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import koxanybak.springframework.cardgame.integration.helper.CardgameTest;
import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.repository.jpa.CardRepository;


public class CardCacheIntegrationTest extends CardgameTest {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() throws Exception {
        addDefaultCardsToDB();
    }

    @Test
    public void cachesCard() {
        Optional<Card> blackCard = cardRepository.findByName("black");
        assertThat(blackCard.get().getImage()).isEqualTo(getCachedCard("black").get().getImage());
        assertThat(blackCard.get().getName()).isEqualTo(getCachedCard("black").get().getName());
    }

    @Test
    public void dropsCacheWhenInsertingNewCards() throws Exception {
        cardRepository.findByName("black"); // Populate cache
        insertCards(Lists.newArrayList(BLACK_CARD_FILE), "not-color"); // Destroy cache by insert 
        assertThat(getCachedCard("black").isEmpty()).isTrue();
    }

    private Optional<Card> getCachedCard(String name) {
        return Optional.ofNullable(cacheManager.getCache("card").get(name, Card.class));
    }
}
