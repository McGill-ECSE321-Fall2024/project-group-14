/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import java.util.*;


// line 40 "model.ump"
// line 152 "model.ump"
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Rating { G, PG, PG_13, R }
  public enum Ranking { OneStar, TwoStar, ThreeStar, FourStar, FiveStar }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private String name;
  private String description;
  private String category;
  private int price;
  private int quantity;
  private Rating rating;
  private String picture;

  //Game Associations
  private Order order;
  private Promotion promotion;
  private List<Review> reviews;
  private List<Customer> customers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(String aName, String aDescription, String aCategory, int aPrice, int aQuantity, Rating aRating, String aPicture, Order aOrder)
  {
    name = aName;
    description = aDescription;
    category = aCategory;
    price = aPrice;
    quantity = aQuantity;
    rating = aRating;
    picture = aPicture;
    if (!setOrder(aOrder))
    {
      throw new RuntimeException("Unable to create Game due to aOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    reviews = new ArrayList<Review>();
    customers = new ArrayList<Customer>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCategory(String aCategory)
  {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(Rating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setPicture(String aPicture)
  {
    boolean wasSet = false;
    picture = aPicture;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getCategory()
  {
    return category;
  }

  public int getPrice()
  {
    return price;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public Rating getRating()
  {
    return rating;
  }

  public String getPicture()
  {
    return picture;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_GetOne */
  public Promotion getPromotion()
  {
    return promotion;
  }

  public boolean hasPromotion()
  {
    boolean has = promotion != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (aNewOrder != null)
    {
      order = aNewOrder;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPromotion(Promotion aNewPromotion)
  {
    boolean wasSet = false;
    if (promotion != null && !promotion.equals(aNewPromotion) && equals(promotion.getGame()))
    {
      //Unable to setPromotion, as existing promotion would become an orphan
      return wasSet;
    }

    promotion = aNewPromotion;
    Game anOldGame = aNewPromotion != null ? aNewPromotion.getGame() : null;

    if (!this.equals(anOldGame))
    {
      if (anOldGame != null)
      {
        anOldGame.promotion = null;
      }
      if (promotion != null)
      {
        promotion.setGame(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(Review.Ranking aRanking, String aDescription, Customer aCustomer)
  {
    return new Review(aRanking, aDescription, aCustomer, this);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aReview.setGame(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    customers.add(aCustomer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (customers.contains(aCustomer))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    order = null;
    Promotion existingPromotion = promotion;
    promotion = null;
    if (existingPromotion != null)
    {
      existingPromotion.delete();
      existingPromotion.setGame(null);
    }
    while (reviews.size() > 0)
    {
      Review aReview = reviews.get(reviews.size() - 1);
      aReview.delete();
      reviews.remove(aReview);
    }
    
    customers.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "category" + ":" + getCategory()+ "," +
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "," +
            "picture" + ":" + getPicture()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "promotion = "+(getPromotion()!=null?Integer.toHexString(System.identityHashCode(getPromotion())):"null");
  }
}