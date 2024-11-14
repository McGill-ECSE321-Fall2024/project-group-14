package ca.mcgill.ecse321_group14.GameShop.service;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String aPassword, String aEmail, String aUsername, int aCardNumber, Date aCardExpiryDate, String aAddress){
        if (aPassword == null || aPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Customer password cannot be empty.");
        }
        if (aEmail == null || aEmail.trim().length() == 0) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }
        if (aUsername == null || aUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Customer username cannot be empty.");
        }
        if (aCardNumber == 0) {
            throw new IllegalArgumentException("Customer card number cannot be empty.");
        }
        if (aCardExpiryDate == null) {
            throw new IllegalArgumentException("Customer card expiry date cannot be empty.");
        }
        if (aAddress == null || aAddress.trim().length() == 0) {
            throw new IllegalArgumentException("Customer address cannot be empty.");
        }
        Customer customer = new Customer(aPassword, aEmail, aUsername, aCardNumber, aCardExpiryDate, aAddress);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer getCustomerByEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        return customer;
    }

    @Transactional
    public Customer getCustomerById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Customer id cannot be empty.");
        }
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        return customer;
    }

    @Transactional
    public Customer updateCustomer(int id, String aPassword, String aEmail, String aUsername, int aCardNumber, Date aCardExpiryDate, String aAddress){
        if (aPassword == null || aPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Customer password cannot be empty.");
        }
        if (aEmail == null || aEmail.trim().length() == 0) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }
        if (aUsername == null || aUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Customer username cannot be empty.");
        }
        if (aCardNumber == 0) {
            throw new IllegalArgumentException("Customer card number cannot be empty.");
        }
        if (aCardExpiryDate == null) {
            throw new IllegalArgumentException("Customer card expiry date cannot be empty.");
        }
        if (aAddress == null || aAddress.trim().length() == 0) {
            throw new IllegalArgumentException("Customer address cannot be empty.");
        }
        Customer customer = customerRepository.findCustomerById(id);
        customer.setPassword(aPassword);
        customer.setUsername(aUsername);
        customer.setCardNumber(aCardNumber);
        customer.setCardExpiryDate(aCardExpiryDate);
        customer.setAddress(aAddress);
        customerRepository.save(customer);
        return customer;

    }

    @Transactional
    public void deleteCustomer(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Customer id cannot be empty.");
        }
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        customerRepository.delete(customer);
    }

    @Transactional
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @Transactional
    public boolean loginCustomer(String email, String password) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Customer password cannot be empty.");
        }
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            return false;
        }
        return customer.getPassword().equals(password) && customer.getEmail().equals(email);
    }
 
    
}
