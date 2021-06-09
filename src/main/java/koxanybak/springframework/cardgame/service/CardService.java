package koxanybak.springframework.cardgame.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import koxanybak.springframework.cardgame.model.Card;
import koxanybak.springframework.cardgame.repository.jpa.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    public Iterable<Card> create(MultipartFile[] files) throws IOException {
        List<Card> cards = Stream.of(files).map(file -> {
            try {
                return (
                    new Card(null, file.getOriginalFilename(), file.getBytes())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
       cards.stream().forEach(im -> {
            System.out.println(im.getFileName());
            System.out.println(im.getImage().length);
        });
        return cardRepository.saveAll(cards);
    }

    public Optional<Card> findOne(String fileName) {
        return cardRepository.findByFileName(fileName);
    }
}
