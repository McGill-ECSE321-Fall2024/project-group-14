package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;

import java.sql.Date;


public class CustomerRequestDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private int cardNumber;
    private Date cardExpiryDate;
    private String address;

    public CustomerRequestDto() {
    }

    public CustomerRequestDto(int id, String username, String email, String password, int cardNumber, Date cardExpiryDate, String address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.address = address;
    }
    
    public Customer toModel(){

    }
    
    public CustomerRequestDto(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.cardNumber = customer.getCardNumber();
        this.cardExpiryDate = customer.getCardExpiryDate();
        this.address = customer.getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

