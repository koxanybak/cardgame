package koxanybak.springframework.cardgame.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import koxanybak.springframework.cardgame.dto.round.RoundGetOneDTO;
import koxanybak.springframework.cardgame.integration.helper.CardgameTest;

public class RoundIntegrationTest extends CardgameTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setupCards() throws Exception {
        addDefaultCardsToDB();
    }

    @Test
    public void createsRound() throws Exception {
        insertRound("color")
            .andExpect(status().is(201))
            .andExpect(jsonPath("$.id", IsNot.not(IsEmptyString.emptyOrNullString())));
    }

    @Test
    public void getsRoundData() throws Exception {
        String idInDb = JsonPath.read(
            insertRound("color").andReturn().getResponse().getContentAsString(),
            "$.id"
        );

        RoundGetOneDTO roundInDb = getRoundByDeckAndId(idInDb, "color");
        assertThat(roundInDb.getCardNames())
            .contains("blue", "yellow", "red")
            .doesNotContain("black");
    }

    @Test
    public void getsNextCard() throws Exception {
        String idInDb = JsonPath.read(
            insertRound("color").andReturn().getResponse().getContentAsString(),
            "$.id"
        );

        mockMvc.perform(get("/api/deck/color/round/{id}/next", idInDb))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    private ResultActions insertRound(String deckName) throws Exception {
        return mockMvc.perform(post("/api/deck/{deckName}/round", deckName));
    }

    private RoundGetOneDTO getRoundByDeckAndId(String roundId, String deckName) throws Exception {
        MvcResult res = mockMvc.perform(get("/api/deck/{deckName}/round/{id}", deckName, roundId))
            .andExpect(status().is(200))
            .andReturn();
        RoundGetOneDTO roundReturned = objectMapper.readValue(
            res.getResponse().getContentAsString(),
            new TypeReference<RoundGetOneDTO>(){}
        );
        return roundReturned;
    }
}
