/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;
// line 90 "model.ump"
// line 183 "model.ump"
@Entity
public class Policy
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Policy Attributes
  @Id
  @GeneratedValue
  private int policyId;
  private String description;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  protected Policy() {}

  public Policy(String aDescription)
  {
    description = aDescription;
    
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

  public int getPolicyId()
  {
    return policyId;
  }

  public String getDescription()
  {
    return description;
  }


  public String toString()
  {
    return super.toString() + "["+
            "policyId" + ":" + getPolicyId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator");
  }
}