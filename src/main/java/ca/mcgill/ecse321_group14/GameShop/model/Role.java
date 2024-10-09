/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


package ca.mcgill.ecse321_group14.GameShop.model;
// line 8 "model.ump"
// line 115 "model.ump"
public class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Role Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role(User aUser)
  {
    if (aUser == null || aUser.getRole() != null)
    {
      throw new RuntimeException("Unable to create Role due to aUser. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    user = aUser;
  }

  public Role(String aIdForUser, String aPasswordForUser, String aEmailForUser, String aUsernameForUser)
  {
    user = new User(aIdForUser, aPasswordForUser, aEmailForUser, aUsernameForUser, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }

  public void delete()
  {
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.delete();
    }
  }

}