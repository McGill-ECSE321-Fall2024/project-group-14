/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;

// line 2 "model.ump"
// line 110 "model.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String id;
  private String password;
  private String email;
  private String username;

  //User Associations
  private Role role;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aId, String aPassword, String aEmail, String aUsername, Role aRole)
  {
    id = aId;
    password = aPassword;
    email = aEmail;
    username = aUsername;
    if (aRole == null || aRole.getUser() != null)
    {
      throw new RuntimeException("Unable to create User due to aRole. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    role = aRole;
  }

  public User(String aId, String aPassword, String aEmail, String aUsername)
  {
    id = aId;
    password = aPassword;
    email = aEmail;
    username = aUsername;
    role = new Role(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template association_GetOne */
  public Role getRole()
  {
    return role;
  }

  public void delete()
  {
    Role existingRole = role;
    role = null;
    if (existingRole != null)
    {
      existingRole.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "username" + ":" + getUsername()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role = "+(getRole()!=null?Integer.toHexString(System.identityHashCode(getRole())):"null");
  }
}