/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;

// line 99 "model.ump"
// line 188 "model.ump"
public class Reply
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reply Attributes
  private String description;

  //Reply Associations
  private Review review;
  private Manager manager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reply(String aDescription, Review aReview, Manager aManager)
  {
    description = aDescription;
    if (!setReview(aReview))
    {
      throw new RuntimeException("Unable to create Reply due to aReview. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setManager(aManager))
    {
      throw new RuntimeException("Unable to create Reply due to aManager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setReview(Review aNewReview)
  {
    boolean wasSet = false;
    if (aNewReview != null)
    {
      review = aNewReview;
      wasSet = true;
    }
    return wasSet;
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
    review = null;
    manager = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}