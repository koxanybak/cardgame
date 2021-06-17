package koxanybak.springframework.cardgame.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import koxanybak.springframework.cardgame.dto.card.CardGetAllDTO;
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

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
        @RequestParam("images[]") List<MultipartFile> images,
        @RequestParam("decks") List<String> decks
    ) throws IOException {
        cardService.create(images, decks);
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
    public List<Card> getAllCards(@RequestParam(value = "deck", required = false) String deck) {
        List<Card> cardsInDb = deck == null ? cardService.findAll() : cardService.findAll(deck);
        Type listType = new TypeToken<List<CardGetAllDTO>>(){}.getType(); 
        return modelMapper.map(cardsInDb, listType);
    }
}
