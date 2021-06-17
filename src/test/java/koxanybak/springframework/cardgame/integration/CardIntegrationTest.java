package koxanybak.springframework.cardgame.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MvcResult;

import koxanybak.springframework.cardgame.dto.card.CardGetAllDTO;
import koxanybak.springframework.cardgame.integration.helper.CardgameTest;

public class CardIntegrationTest extends CardgameTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void providesCardController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertThat(servletContext).isNotNull();
        assertThat(servletContext instanceof MockServletContext).isTrue();
        assertThat(webApplicationContext.getBean("cardController")).isNotNull();
    }

    @Test
    public void savesPostedJPGs() throws Exception {
        File[] testJPGs = new File(
            TEST_DATA_DIRECTORY
        ).listFiles();
        insertCards(Lists.newArrayList(testJPGs), "color", "not-color")
            .andExpect(status().is(201));
    }

    @Test
    public void doesNotSaveImagesWithoutDecks() throws Exception {
        insertCards(Lists.newArrayList(YELLOW_CARD_FILE))
            .andExpect(status().is(400));
    }

    @Nested
    class WithCardsInDatabase {

        @BeforeEach
        public void setupDatabase() throws Exception {
            addDefaultCardsToDB();
        }

        @Test
        public void getsCorrectCardsInCorrectForm() throws Exception {
            assertThat(cardsForDeck("color"))
                .doesNotContain("black")
                .contains("red", "yellow", "blue");
        }

        @Test
        public void updatesCardsCorrectly() throws Exception {
            insertCards(Lists.newArrayList(BLACK_CARD_FILE), "color", "not-color");
            insertCards(Lists.newArrayList(BLUE_CARD_FILE), "not-color");

            assertThat(cardsForDeck("color"))
                .doesNotContain("blue")
                .contains("black", "yellow", "red");

            assertThat(cardsForDeck("not-color"))
                .doesNotContain("red", "yellow")
                .contains("black", "blue");
        }

        @Test
        public void getsSingleCardImage() throws Exception {
            mockMvc.perform(get("/api/card/red/image"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
        }
    }

    private List<String> cardsForDeck(String deck) throws Exception {
        MvcResult res = mockMvc.perform(get("/api/card?deck={deck}", deck))
            .andExpect(status().is(200))
            .andReturn();
        List<CardGetAllDTO> cardsReturned = objectMapper.readValue(
            res.getResponse().getContentAsString(),
            new TypeReference<List<CardGetAllDTO>>(){}
        );
        return cardsReturned.stream().map(card -> card.getCardName()).toList();
    }
}
