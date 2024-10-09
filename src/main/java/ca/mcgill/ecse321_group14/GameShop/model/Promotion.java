/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;

// line 83 "model.ump"
// line 178 "model.ump"
public class Promotion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Promotion Attributes
  private String description;
  private int discount;

  //Promotion Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Promotion(String aDescription, int aDiscount, Game aGame)
  {
    description = aDescription;
    discount = aDiscount;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create promotion due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (aNewGame == null)
    {
      //Unable to setGame to null, as promotion must always be associated to a game
      return wasSet;
    }
    
    Promotion existingPromotion = aNewGame.getPromotion();
    if (existingPromotion != null && !equals(existingPromotion))
    {
      //Unable to setGame, the current game already has a promotion, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Game anOldGame = game;
    game = aNewGame;
    game.setPromotion(this);

    if (anOldGame != null)
    {
      anOldGame.setPromotion(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.setPromotion(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "discount" + ":" + getDiscount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}