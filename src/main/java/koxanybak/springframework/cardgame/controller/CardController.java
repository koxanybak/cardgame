package koxanybak.springframework.cardgame.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/")
    public void create(@RequestParam("images[]") MultipartFile[] images) throws IOException {
        Arrays.stream(images).forEach(im -> {
            System.out.println(im.getOriginalFilename());
            System.out.println(im.getSize());
        });
        cardService.create(images);
    }

    @GetMapping(value="/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getMethodName(@PathVariable(value = "fileName") String fileName) {
        Optional<Card> cardInDb = cardService.findOne(fileName);
        if (cardInDb.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return cardInDb.get().getImage();
    }
    
}
