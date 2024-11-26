package ca.mcgill.ecse321_group14.GameShop.model;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
<<<<<<< HEAD:GameShop-Backend/src/main/java/ca/mcgill/ecse321_group14/GameShop/model/Orderitem.java
public class OrderItems {
    @EmbeddedId
    private Key key;

    protected OrderItems () {}

    public OrderItems(Key key){
=======
public class Orderitem {
    @EmbeddedId
    private Key key;

    public Orderitem () {}

    public Orderitem(Key key){
>>>>>>> main:src/main/java/ca/mcgill/ecse321_group14/GameShop/model/Orderitem.java
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    @Embeddable
    public static class Key implements Serializable {
        @ManyToOne
        private Game game;
        @ManyToOne
        private Order order;

        public Key(){
            super();
        }

        public Key(Game game, Order order){
            this.game = game;
            this.order = order;
        }

        public Game getGame() {
            return game;
        }

        public Order getOrder() {
            return order;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return game.equals(key.game) && order.equals(key.order);
        }

        @Override
        public int hashCode() {
            return game.hashCode() + order.hashCode();
        }
    }
}
