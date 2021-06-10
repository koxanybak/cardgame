package koxanybak.springframework.cardgame.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping(value="/")
    public RoundGetOneDTO create() {
        RoundRedis newRound = roundService.create();
        return modelMapper.map(newRound, RoundGetOneDTO.class);
    }
    
}
