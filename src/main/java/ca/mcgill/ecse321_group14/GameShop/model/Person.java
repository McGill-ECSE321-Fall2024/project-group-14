/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321_group14.GameShop.model;
import java.util.Objects;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role_type")
public abstract class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  @Id
  @GeneratedValue
  private int id;
  private String password;
  private String email;
  private String username;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Person() {}

  public Person(String aPassword, String aEmail, String aUsername)
  {
    password = aPassword;
    email = aEmail;
    username = aUsername;
  }

  //------------------------
  // INTERFACE
  //------------------------


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

  public int getId()
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

  public void delete()
  {}

  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
               Objects.equals(email, person.email) &&
               Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }

  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "username" + ":" + getUsername()+ "]";
  }
}