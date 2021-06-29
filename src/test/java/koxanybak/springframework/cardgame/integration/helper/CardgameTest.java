package koxanybak.springframework.cardgame.integration.helper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import koxanybak.springframework.cardgame.CardgameApplication;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = CardgameApplication.class)
@WebAppConfiguration
public abstract class CardgameTest {
    protected static final String TEST_DATA_DIRECTORY = "./src/test/java/koxanybak/springframework/cardgame/testdata";
    
    protected static final File BLACK_CARD_FILE = new File(TEST_DATA_DIRECTORY + "/black.jpg");
    protected static final File YELLOW_CARD_FILE = new File(TEST_DATA_DIRECTORY + "/yel.low.jpg");
    protected static final File RED_CARD_FILE = new File(TEST_DATA_DIRECTORY + "/red.JPG");
    protected static final File BLUE_CARD_FILE = new File(TEST_DATA_DIRECTORY + "/blue.jpg");
    
    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected static MockMvc mockMvc;
    @BeforeEach
    public void setupMockMVC() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    protected static ResultActions insertCards(
        List<File> cardImages,
        String... decks
    ) throws Exception {
        MockMultipartHttpServletRequestBuilder requestBuilder = multipart("/api/card");
        cardImages.forEach(image -> {
            try {
                MockMultipartFile mFile = new MockMultipartFile(
                    "images[]",
                    image.getName(),
                    MediaType.IMAGE_JPEG_VALUE,
                    new FileInputStream(image)
                );
                requestBuilder.file(mFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        });
        
        if (!Arrays.isNullOrEmpty(decks)) {
            requestBuilder.param("decks", decks);
        }

        return mockMvc.perform(requestBuilder);
    }

    // TODO: Could be done straight using the repository
    protected static void addDefaultCardsToDB() throws Exception {
        insertCards(Lists.newArrayList(BLACK_CARD_FILE), "not-color");
        insertCards(Lists.newArrayList(
            BLUE_CARD_FILE,
            YELLOW_CARD_FILE,
            RED_CARD_FILE
        ), "color");
    }
}
