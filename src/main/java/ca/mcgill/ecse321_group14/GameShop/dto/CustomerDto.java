package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;

import java.sql.Date;

public class CustomerDto extends PersonDto{
    private int cardNumber;
    private Date cardExpiryDate;
    private String address;

    public CustomerDto() {
        super();
    }

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.cardNumber = customer.getCardNumber();
        this.cardExpiryDate = customer.getCardExpiryDate();
        this.address = customer.getAddress();
    }

    public CustomerDto(int id, String username, String email, String password, int cardNumber, Date cardExpiryDate, String address) {
        super(id, username, email, password);
        //customer has these extra fields
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.address = address;
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
