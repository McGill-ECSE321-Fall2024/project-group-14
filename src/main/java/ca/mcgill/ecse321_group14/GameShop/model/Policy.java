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

  //Policy Associations
  @ManyToOne
  private Manager manager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  protected Policy() {}

  public Policy(String aDescription, Manager aManager)
  {
    description = aDescription;
    if (!setManager(aManager))
    {
      throw new RuntimeException("Unable to create Policy due to aManager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public int getPolicyId()
  {
    return policyId;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (aNewManager != null)
    {
      manager = aNewManager;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    manager = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "policyId" + ":" + getPolicyId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}