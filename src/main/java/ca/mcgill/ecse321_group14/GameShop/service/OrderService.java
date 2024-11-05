package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Order createOrder(Date date, Customer customer) {
        if (date == null || customer == null) {
            throw new IllegalArgumentException("All fields must be filled!");
        }
        if (customerRepository.findCustomerById(customer.getId()) == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        Order order = new Order(date, customer);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @Transactional
    public void deleteOrder(int id) {
        if (orderRepository.findOrderById(id) == null) {
            throw new IllegalArgumentException("Order does not exist!");
        }
        orderRepository.deleteOrderById(id);
    }

}
