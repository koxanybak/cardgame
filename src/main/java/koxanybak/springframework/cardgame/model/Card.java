package koxanybak.springframework.cardgame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Card")
@Table(name = "card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @Column
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL } )
    @JoinTable(
        name = "card_deck",
        joinColumns = @JoinColumn(name = "card_name"),
        inverseJoinColumns = @JoinColumn(name = "deck_name")
    )
    private List<Deck> decks = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;
}
