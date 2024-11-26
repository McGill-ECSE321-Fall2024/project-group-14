/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;
// line 63 "model.ump"
// line 165 "model.ump"
@Entity
public class GameApprovalRequest
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { PendingApproval, Approved, Rejected }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameApprovalRequest Attributes
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String description;
  private String category;
  private String picture;
  private Status status;

  //GameApprovalRequest Associations
  @ManyToOne
  private Employee requestCreator;
  @ManyToOne
  private Manager requestApprover;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  protected GameApprovalRequest() {}

  public GameApprovalRequest(String aName, String aDescription, String aCategory, String aPicture, Status aStatus, Employee aRequestCreator, Manager aRequestApprover)
  {
    name = aName;
    description = aDescription;
    category = aCategory;
    picture = aPicture;
    status = aStatus;
    if (!setRequestCreator(aRequestCreator))
    {
      throw new RuntimeException("Unable to create GameApprovalRequest due to aRequestCreator. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setRequestApprover(aRequestApprover))
    {
      throw new RuntimeException("Unable to create GameApprovalRequest due to aRequestApprover. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setPicture(String aPicture)
  {
    boolean wasSet = false;
    picture = aPicture;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
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

  public String getPicture()
  {
    return picture;
  }

  public Status getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public Employee getRequestCreator()
  {
    return requestCreator;
  }
  /* Code from template association_GetOne */
  public Manager getRequestApprover()
  {
    return requestApprover;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRequestCreator(Employee aNewRequestCreator)
  {
    boolean wasSet = false;
    if (aNewRequestCreator != null)
    {
      requestCreator = aNewRequestCreator;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRequestApprover(Manager aNewRequestApprover)
  {
    boolean wasSet = false;
    if (aNewRequestApprover != null)
    {
      requestApprover = aNewRequestApprover;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    requestCreator = null;
    requestApprover = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "category" + ":" + getCategory()+ "," +
            "picture" + ":" + getPicture()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestCreator = "+(getRequestCreator()!=null?Integer.toHexString(System.identityHashCode(getRequestCreator())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestApprover = "+(getRequestApprover()!=null?Integer.toHexString(System.identityHashCode(getRequestApprover())):"null");
  }
}