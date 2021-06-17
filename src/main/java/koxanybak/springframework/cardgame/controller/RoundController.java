package koxanybak.springframework.cardgame.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import koxanybak.springframework.cardgame.dto.round.RoundGetOneDTO;
import koxanybak.springframework.cardgame.model.RoundRedis;
import koxanybak.springframework.cardgame.service.RoundService;



@RestController
@RequestMapping("/api/deck/{deckName}/round")
public class RoundController {
    
    @Autowired
    private RoundService roundService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value="")
    @ResponseStatus(HttpStatus.CREATED)
    public RoundGetOneDTO create(@PathVariable(value = "deckName") String deckName) {
        RoundRedis newRound = roundService.create(deckName);
        return modelMapper.map(newRound, RoundGetOneDTO.class);
    }
    
    @GetMapping(value = "/{roundId}/next", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getNextCard(@PathVariable(value = "roundId") UUID roundId) {
        return this.roundService.getNextCard(roundId);
    }
}
