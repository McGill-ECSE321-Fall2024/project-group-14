package ca.mcgill.ecse321_group14.GameShop.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;
import jakarta.transaction.Transactional;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderitemRepository orderitemRepository;

    @Transactional
    public Order createOrder(int customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        Order order = new Order(LocalDate.now(), customer);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @Transactional
    public Order getOrder(int id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new IllegalArgumentException("Order does not exist!");
        }
        return order;
    }

    @Transactional
    public void deleteOrder(int id) {
        if (orderRepository.findOrderById(id) == null) {
            throw new IllegalArgumentException("Order does not exist!");
        }
        orderRepository.deleteOrderById(id);
    }

}
