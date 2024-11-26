package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Person;

public class PersonDto {
    public int id;
    public String username;
    public String email;
    public String password;
    public PersonDto() {
    }
    public PersonDto(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public PersonDto(Person person){
        this.id = person.getId();
        this.username = person.getUsername();
        this.email = person.getEmail();
        this.password = person.getPassword();
    }
    public int getPersonId() {
        return id;
    }
    public void setPersonId(int id) {
        this.id = id;
    }
    public String getPersonUsername() {
        return username;
    }
    public void setPersonUsername(String username) {
        this.username = username;
    }
    public String getPersonEmail() {
        return email;
    }
    public void setPersonEmail(String email) {
        this.email = email;
    }
    public String getPersonPassword() {
        return password;
    }
    public void setPersonPassword(String password) {
        this.password = password;
    }
}
