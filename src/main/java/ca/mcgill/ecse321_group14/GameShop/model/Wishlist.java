package ca.mcgill.ecse321_group14.GameShop.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist
{
    @EmbeddedId
    private Key key;

    protected Wishlist () {}

    public Wishlist
    (Key key){
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    @Embeddable
    public static class Key implements Serializable {
        @ManyToOne
        private Game wish;
        @ManyToOne
        private Customer buyer;

        public Key(){
            super();
        }

        public Key(Game wish, Customer buyer){
            this.wish = wish;
            this.buyer = buyer;
        }

        public Game getWish() {
            return wish;
        }

        public Customer getBuyer() {
            return buyer;
        }
    }
}
