/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;

// line 75 "model.ump"
// line 172 "model.ump"
@Entity
public class Review
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Ranking { OneStar, TwoStar, ThreeStar, FourStar, FiveStar }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue
  private int id;
  private Ranking ranking;
  private String description;

  //Review Associations
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Review() {}

  public Review(Ranking aRanking, String aDescription, Customer aCustomer, Game aGame)
  {
    ranking = aRanking;
    description = aDescription;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Review due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  public int getId()
  {
    return id;
  }

  public boolean setRanking(Ranking aRanking)
  {
    boolean wasSet = false;
    ranking = aRanking;
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

  public Ranking getRanking()
  {
    return ranking;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeReview(this);
    }
    game.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    customer = null;
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ranking" + "=" + (getRanking() != null ? !getRanking().equals(this)  ? getRanking().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}