package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;

public class PersonListDtos {
    // Default constructor
    public PersonListDtos() {
    }

    private List<PersonResponseDto> persons;

    public PersonListDtos(List<PersonResponseDto> persons) {
        this.persons = persons;
    }

    public List<PersonResponseDto> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonResponseDto> persons) {
        this.persons = persons;
    }
}
