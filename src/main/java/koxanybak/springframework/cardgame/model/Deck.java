package koxanybak.springframework.cardgame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "deck")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deck {
    
    @Id
    @Column
    private String name;
}
