/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321_group14.GameShop.model;
import jakarta.persistence.*;
import java.sql.Date;

// line 15 "model.ump"
// line 126 "model.ump"
@Entity
@PrimaryKeyJoinColumn(name="person_id")
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes

//TODO ALL OF THESE BC THEY CANT BE PRIVATAE
  private int cardNumber; 
  private Date cardExpiryDate;
  private String address;
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate requires a default constructor
  public Customer() {}

  public Customer(String aPassword, String aEmail, String aUsername, int aCardNumber, Date aCardExpiryDate, String aAddress)
  {
    super(aPassword, aEmail, aUsername);
    cardNumber = aCardNumber;
    cardExpiryDate = aCardExpiryDate;
    address = aAddress;
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

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardNumber" + ":" + getCardNumber()+ "," +
            "address" + ":" + getAddress()+ "," +
            System.getProperties().getProperty("line.separator") +
            "  " + "cardExpiryDate" + "=" + (getCardExpiryDate() != null ? !getCardExpiryDate().equals(this)  ? getCardExpiryDate().toString().replaceAll("  ","    ") : "this" : "null");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Customer)) return false;
    Customer customer = (Customer) o;
    return super.equals(o) && cardNumber == customer.cardNumber && cardExpiryDate.equals(customer.cardExpiryDate) && address.equals(customer.address);
  }

  @Override
  public int hashCode() {
    return super.hashCode() + cardNumber + cardExpiryDate.hashCode() + address.hashCode();
  }
}