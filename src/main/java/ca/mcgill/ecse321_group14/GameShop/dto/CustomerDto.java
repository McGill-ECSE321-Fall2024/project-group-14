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

    public CustomerDto(int id, String username, String email, String password, int cardNumber, Date cardExpiryDate, String address) {
        super(id, username, email, password);
        //customer has these extra fields
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.address = address;
    }
    
    public Customer toModel(){
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setUsername(this.getUsername());
        customer.setEmail(this.getEmail());
        customer.setPassword(this.getPassword());
        customer.setCardNumber(this.cardNumber);
        customer.setCardExpiryDate(this.cardExpiryDate);
        customer.setAddress(this.address);
        return customer;
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
