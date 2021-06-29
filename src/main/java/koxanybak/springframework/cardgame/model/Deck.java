package koxanybak.springframework.cardgame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "deck")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Deck implements Serializable {
    
    @Id
    @Column
    private String name;
}
