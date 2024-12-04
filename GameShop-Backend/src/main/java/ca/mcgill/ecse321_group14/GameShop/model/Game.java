/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

// line 40 "model.ump"
// line 152 "model.ump"
@Entity
public class Game {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum Rating {
    G, PG, PG_13, R
  }

  public enum Ranking {
    OneStar, TwoStar, ThreeStar, FourStar, FiveStar
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Game Attributes
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String description;
  private String category;
  private int price;
  private int quantity;
  @Enumerated(EnumType.STRING)
  private Rating rating;
  private String picture;
  private int discountedprice;

  // Game Associations
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Review> reviews;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  // Hibernate requires a default constructor
  public Game() {
    reviews = new ArrayList<Review>();
  }

  public Game(String aName, String aDescription, String aCategory, int aPrice, int aQuantity, Rating aRating,
      String aPicture) {
    name = aName;
    description = aDescription;
    category = aCategory;
    price = aPrice;
    quantity = aQuantity;
    rating = aRating;
    picture = aPicture;
    reviews = new ArrayList<Review>();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public int getDiscountedprice() {
    return discountedprice != 0 ? discountedprice : price;
  }

  public void setDiscountedprice(int discountedprice) {
    this.discountedprice = discountedprice;
  }
  public boolean setCategory(String aCategory) {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice) {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity) {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(Rating aRating) {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setPicture(String aPicture) {
    boolean wasSet = false;
    picture = aPicture;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getCategory() {
    return category;
  }

  public int getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public Rating getRating() {
    return rating;
  }

  public String getPicture() {
    return picture;
  }

  /* Code from template association_GetMany */
  public Review getReview(int index) {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews() {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews() {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews() {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview) {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetMany */

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Review addReview(Review.Ranking aRanking, String aDescription, Customer aCustomer) {
    return new Review(aRanking, aDescription, aCustomer, this);
  }

  public boolean addReview(Review aReview) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      return false;
    }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame) {
      aReview.setGame(this);
    } else {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview) {
    boolean wasRemoved = false;
    // Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame())) {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (addReview(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } else {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */

  public void delete() {
    while (reviews.size() > 0) {
      Review aReview = reviews.get(reviews.size() - 1);
      aReview.delete();
      reviews.remove(aReview);
    }

  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "," +
        "name" + ":" + getName() + "," +
        "description" + ":" + getDescription() + "," +
        "category" + ":" + getCategory() + "," +
        "price" + ":" + getPrice() + "," +
        "quantity" + ":" + getQuantity() + "," +
        "picture" + ":" + getPicture() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "rating" + "="
        + (getRating() != null ? !getRating().equals(this) ? getRating().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + System.getProperties().getProperty("line.separator");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Game))
      return false;
    Game game = (Game) o;
    return id == game.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}