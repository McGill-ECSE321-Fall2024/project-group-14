package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;

public class CustomerListDtos {
    // Default constructor
    public CustomerListDtos() {
    }

    private List<CustomerResponseDto> customers;

    public CustomerListDtos(List<CustomerResponseDto> customers) {
        this.customers = customers;
    }

    public List<CustomerResponseDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerResponseDto> customers) {
        this.customers = customers;
    }
}
