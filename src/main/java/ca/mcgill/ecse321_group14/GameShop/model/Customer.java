/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321_group14.GameShop.model;

import java.sql.Date;

// line 15 "model.ump"
// line 126 "model.ump"
public class Customer extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int cardNumber;
  private Date cardExpiryDate;
  private String address;
  private String id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(User aUser, int aCardNumber, Date aCardExpiryDate, String aAddress, String aId)
  {
    super(aUser);
    cardNumber = aCardNumber;
    cardExpiryDate = aCardExpiryDate;
    address = aAddress;
    id = aId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCardNumber(int aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardExpiryDate(Date aCardExpiryDate)
  {
    boolean wasSet = false;
    cardExpiryDate = aCardExpiryDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getCardNumber()
  {
    return cardNumber;
  }

  public Date getCardExpiryDate()
  {
    return cardExpiryDate;
  }

  public String getAddress()
  {
    return address;
  }

  public String getId()
  {
    return id;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardNumber" + ":" + getCardNumber()+ "," +
            "address" + ":" + getAddress()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cardExpiryDate" + "=" + (getCardExpiryDate() != null ? !getCardExpiryDate().equals(this)  ? getCardExpiryDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}