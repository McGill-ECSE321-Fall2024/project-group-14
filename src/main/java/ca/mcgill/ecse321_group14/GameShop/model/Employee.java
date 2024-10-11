/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;
// line 27 "model.ump"
// line 136 "model.ump"
@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Employee extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  protected Employee() {}

  public Employee(int aId, String aPassword, String aEmail, String aUsername)
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