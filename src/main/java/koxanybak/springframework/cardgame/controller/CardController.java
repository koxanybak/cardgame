package koxanybak.springframework.cardgame.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import koxanybak.springframework.cardgame.dto.card.CardGetOneDTO;
import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.service.CardService;


@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public void create(
        @RequestParam("images[]") MultipartFile[] images,
        @RequestParam("decks") String[] decks
    ) throws IOException {
        cardService.create(images, Arrays.asList(decks));
    }

    @GetMapping(value="/{cardName}")
    public CardGetOneDTO getCard(@PathVariable(value = "cardName") String cardName) {
        Card cardInDb = cardService.findOne(cardName);
        return modelMapper.map(cardInDb, CardGetOneDTO.class);
    }

    @GetMapping(value="/{cardName}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCardImage(@PathVariable(value = "cardName") String cardName) {
        Card cardInDb = cardService.findOne(cardName);
        return cardInDb.getImage();
    }
    
    @GetMapping(value = "")
    public List<Card> getAllCards() {
        List<Card> cardsInDb = cardService.findAll();
        // TODO mapping DTO
        return cardsInDb;
    }
}
