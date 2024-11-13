import java.util.List;

package ca.mcgill.ecse321_group14.GameShop.dto;


public class OrderRequestDto{
    private String customerName;
    private List<String> gameTitles;

    public OrderRequestDto(String customerName, List<String> gameTitles) {
        this.customerName = customerName;
        this.gameTitles = gameTitles;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getGameTitles() {
        return gameTitles;
    }

    public void setGameTitles(List<String> gameTitles) {
        this.gameTitles = gameTitles;
    }
}