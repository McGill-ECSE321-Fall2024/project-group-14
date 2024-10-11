/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Manager extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  protected Manager() {}

  public Manager(int aId, String aPassword, String aEmail, String aUsername)
  {
    super(aId, aPassword, aEmail, aUsername);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}