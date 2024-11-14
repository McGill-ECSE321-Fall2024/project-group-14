/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
// line 83 "model.ump"
// line 178 "model.ump"
@Entity
public class Promotion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Promotion Attributes
  @Id
  @GeneratedValue
  private int id;
  private String description;
  private int discount;

  //Promotion Associations
  @ManyToOne
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Promotion() {}

  public Promotion(String aDescription, int aDiscount, Game aGame)
  {
    description = aDescription;
    discount = aDiscount;
    this.game = aGame;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiscount(int aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public int getPromotionId(){return id;}
  public String getDescription()
  {
    return description;
  }

  public int getDiscount()
  {
    return discount;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToOptionalOne */
  public void setGame(Game aNewGame)
  {
    this.game = aNewGame;
  }

  public void delete()
  {
    this.game = null;
  }

  public int getId()
  {
    return id;
  }

  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "discount" + ":" + getDiscount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}