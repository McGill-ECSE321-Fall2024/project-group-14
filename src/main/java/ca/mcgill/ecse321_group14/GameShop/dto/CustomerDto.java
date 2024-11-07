package ca.mcgill.ecse321_group14.GameShop.dto;

public class CustomerDto extends PersonDto{
    private int cardNumber;
    private String cardExpiryDate;
    private String address;

    public CustomerDto() {
        //call super class
        super();
    }

    public CustomerDto(int id, String username, String email, int cardNumber, String cardExpiryDate, String address) {
        super(id, username, email);
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

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
