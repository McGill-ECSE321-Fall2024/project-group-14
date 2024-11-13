package ca.mcgill.ecse321_group14.GameShop.dto;

public class CustomerResponseDto {
    private String firstName;
    private String lastName;
    private String email;

    public CustomerResponseDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}