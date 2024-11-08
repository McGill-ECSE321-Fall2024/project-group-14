package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;
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
    @Autowired
    private OrderitemRepository orderitemRepository;

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
    public void addOrderItemToOrder(int orderId,Game game) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order does not exist!");
        }
        Orderitem.Key key = new Orderitem.Key(game, order);
        Orderitem orderitem = new Orderitem(key);
        orderitemRepository.save(orderitem);
        orderRepository.save(order);
    }
    @Transactional
    public void deleteOrderItemFromOrder(int orderId, int orderItemId, Game game) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order does not exist!");
        }
        Orderitem.Key key = new Orderitem.Key(game, order);
        Orderitem orderitem = orderitemRepository.findOrderitemByKey(key);
        if (orderitem == null) {
            throw new IllegalArgumentException("Orderitem does not exist!");
        }
        orderitemRepository.deleteOrderitemByKey(key);
        orderRepository.save(order);
    }

}
