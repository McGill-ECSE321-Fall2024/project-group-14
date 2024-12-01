package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Person;

public class PersonResponseDto {
    private int id;
    private String username;
    private String email;

    public PersonResponseDto() {
    }
    public PersonResponseDto(Person person) {
        this.id = person.getId();
        this.username = person.getUsername();
        this.email = person.getEmail();
    }
    public static PersonResponseDto create(Person person){
        return new PersonResponseDto(person);
    }
    public int getPersonId() {
        return id;
    }
    public String getPersonUsername() {
        return username;
    }
    public String getPersonEmail() {
        return email;
    }
    public void setPersonId(int id) {
        this.id = id;
    }
    public void setPersonUsername(String username) {
        this.username = username;
    }
    public void setPersonEmail(String email) {
        this.email = email;
    }
}
