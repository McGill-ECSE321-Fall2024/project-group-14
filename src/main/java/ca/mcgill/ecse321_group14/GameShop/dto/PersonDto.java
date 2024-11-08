package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Person;

public class PersonDto {
    private int id;
    private String username;
    private String email;

    public PersonDto() {
    }
    public PersonDto(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    public PersonDto(Person person){
        this.id = person.getId();
        this.username = person.getUsername();
        this.email = person.getEmail();
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
}
