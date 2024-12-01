package ca.mcgill.ecse321_group14.GameShop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
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
    @Autowired
    private OrderitemService orderitemService;

    @Transactional
    public Order createOrder(int customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        Order order = new Order(LocalDate.now(), customer, false);
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

    @Transactional
    public List<Order> findOrderByCustomerId(int customerId) {
        return orderRepository.findOrderByCustomerId(customerId);
    }

    @Transactional
    public Order markOrderAsPaid(int orderId){
        Order order = getOrder(orderId);
        order.setIsPaid(true);
        return orderRepository.save(order);
    }

    @Transactional
    public void refundOrder(int orderId){
        Order order = orderRepository.findOrderById(orderId);
        if(order == null){
            throw new IllegalArgumentException("Order does not exist!");
        }

        if(!order.getIsPaid()){
            throw new IllegalArgumentException("Order has not been paid for!");
        }

        List<Orderitem> orderItems = orderitemService.findOrderItemsByOrderId(orderId);
        for (Orderitem item : orderItems) {
            orderitemService.deleteOrderitem(item.getKey().getGame(), order); // Assuming this method exists
        }
        orderRepository.deleteById(orderId);

    }

    @Transactional
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }
}
