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

@Entity
@Table(name = "card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @Column(unique = true)
    private String cardName;

    @ManyToMany(cascade = { CascadeType.ALL } )
    @JoinTable(
        joinColumns = @JoinColumn(name = "card_id"),
        inverseJoinColumns = @JoinColumn(name = "deck_id")
    )
    private List<Deck> decks = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;
}
