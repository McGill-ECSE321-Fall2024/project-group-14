import java.util.List;

package ca.mcgill.ecse321_group14.GameShop.dto;


public class OrderResponseDto {
    private Long id;
    private String customerName;
    private List<String> gameTitles;
    private double totalPrice;

    public OrderResponseDto(Long id, String customerName, List<String> gameTitles, double totalPrice) {
        this.id = id;
        this.customerName = customerName;
        this.gameTitles = gameTitles;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getGameTitles() {
        return gameTitles;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}