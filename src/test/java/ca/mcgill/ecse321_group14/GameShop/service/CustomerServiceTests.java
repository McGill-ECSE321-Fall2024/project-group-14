package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CustomerServiceTests {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @SuppressWarnings("null")
    @Test
    public void testCreateCustomer() {
        String password = "password";
        String email = "email";
        String username = "username";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";

        when(customerRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        //act
        Customer createdCustomer = customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);

        //assert
        assertNotNull(createdCustomer);
        assertEquals(password, createdCustomer.getPassword());
        assertEquals(email, createdCustomer.getEmail());
        assertEquals(username, createdCustomer.getUsername());
        assertEquals(cardNumber, createdCustomer.getCardNumber());
        assertEquals(cardExpiryDate, createdCustomer.getCardExpiryDate());
        assertEquals(address, createdCustomer.getAddress());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void testCreateCustomerNullUsername() {
        String password = "password";
        String username = null;
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer username cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testCreateCustomerNullEmail() {
        String password = "password";
        String username = "username";
        String email = null;
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer email cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testCreateCustomerNullPassword() {
        String password = null;
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer password cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testCreateCustomerNullCardNumber() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 0;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer card number cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testCreateCustomerNullCardExpiryDate() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = null;
        String address = "address";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer card expiry date cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testCreateCustomerNullAddress() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer address cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testFindCustomerById() {
        int id = 1;
        Customer customer = new Customer("password", "email", "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerById(id)).thenReturn(customer);

        //act
        Customer foundCustomer = customerService.getCustomerById(id);

        //assert
        assertNotNull(foundCustomer);
        assertEquals(customer, foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());
        assertEquals(customer.getPassword(), foundCustomer.getPassword());
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
        assertEquals(customer.getUsername(), foundCustomer.getUsername());
        assertEquals(customer.getCardNumber(), foundCustomer.getCardNumber());
        assertEquals(customer.getCardExpiryDate(), foundCustomer.getCardExpiryDate());
        assertEquals(customer.getAddress(), foundCustomer.getAddress());
        verify(customerRepository, times(1)).findCustomerById(id);
    }

    @Test
    public void testFindCustomerByNullId() {
        int id = 0;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerById(id);
        });

        // Assert
        assertEquals("Customer id cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).findCustomerById(id);
    }

    @Test
    public void testFindCustomerByNonExistentId() {
        int id = 1;
        when(customerRepository.findCustomerById(id)).thenReturn(null);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerById(id);
        });

        // Assert
        assertEquals("Customer not found.", exception.getMessage());
        verify(customerRepository, times(1)).findCustomerById(id);
    }

    @Test
    public void testFindCustomerByEmail() {
        String email = "email";
        Customer customer = new Customer("password", "email", "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);

        //act
        Customer foundCustomer = customerService.getCustomerByEmail(email);

        //assert
        assertNotNull(foundCustomer);
        assertEquals(customer, foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());
        assertEquals(customer.getPassword(), foundCustomer.getPassword());
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
        assertEquals(customer.getUsername(), foundCustomer.getUsername());
        assertEquals(customer.getCardNumber(), foundCustomer.getCardNumber());
        assertEquals(customer.getCardExpiryDate(), foundCustomer.getCardExpiryDate());
        assertEquals(customer.getAddress(), foundCustomer.getAddress());
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }

    @Test
    public void testFindCustomerByNullEmail() {
        String email = null;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerByEmail(email);
        });

        // Assert
        assertEquals("Customer email cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).findCustomerByEmail(email);
    }

    @Test
    public void testFindCustomerByNonExistentEmail() {
        String email = "email";
        when(customerRepository.findCustomerByEmail(email)).thenReturn(null);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerByEmail(email);
        });

        // Assert
        assertEquals("Customer not found.", exception.getMessage());
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }

    @Test
    public void testUpdateCustomer() {
        String password = "password";
        String email = "email";
        String username = "username";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";
        Customer customer = new Customer("password", "email", "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(customer);
        when(customerRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));


        //act
        Customer updatedCustomer = customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);

        //assert
        assertNotNull(updatedCustomer);
        assertEquals(password, updatedCustomer.getPassword());
        assertEquals(email, updatedCustomer.getEmail());
        assertEquals(username, updatedCustomer.getUsername());
        assertEquals(cardNumber, updatedCustomer.getCardNumber());
        assertEquals(cardExpiryDate, updatedCustomer.getCardExpiryDate());
        assertEquals(address, updatedCustomer.getAddress());
        verify(customerRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findCustomerByEmail(customer.getEmail());
    }

    @Test
    public void testUpdateCustomerNullUsername() {
        String password = "password";
        String username = null;
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer username cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateCustomerNullEmail() {
        String password = "password";
        String username = "username";
        String email = null;
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer email cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateCustomerNullPassword() {
        String password = null;
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer password cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());

    }
    @Test
    public void testUpdateCustomerNullCardNumber() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 0;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = "address";


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer card number cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }
    @Test
    public void testUpdateCustomerNullCardExpiryDate() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = null;
        String address = "address";


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer card expiry date cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }
    @Test
    public void testUpdateCustomerNullAddress() {
        String password = "password";
        String username = "username";
        String email = "email";
        int cardNumber = 123456789;
        Date cardExpiryDate = new Date(2021, 12, 31);
        String address = null;


        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(password, email, username, cardNumber, cardExpiryDate, address);
        });

        // Assert
        assertEquals("Customer address cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).save(any());

    }
    @Test
    public void testDeleteCustomer() {
        int id = 1;
        Customer customer = new Customer("password", "email", "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerById(id)).thenReturn(customer);

        //act
        customerService.deleteCustomer(id);

        //assert

        verify(customerRepository, times(1)).delete(customer);
        verify(customerRepository, times(1)).findCustomerById(id);
    }
    @Test
    public void testDeleteCustomerNullId() {
        int id = 0;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomer(id);
        });

        // Assert
        assertEquals("Customer id cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).delete(any());
        verify(customerRepository, times(0)).findCustomerById(id);
    }
    @Test
    public void testDeleteCustomerNonExistentId() {
        int id = 1;
        when(customerRepository.findCustomerById(id)).thenReturn(null);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomer(id);
        });

        // Assert
        assertEquals("Customer not found.", exception.getMessage());
        verify(customerRepository, times(0)).delete(any());
        verify(customerRepository, times(1)).findCustomerById(id);
    }
    @Test
    public void testDeleteCustomerNullCustomer() {
        int id = 1;
        when(customerRepository.findCustomerById(id)).thenReturn(null);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomer(id);
        });

        // Assert
        assertEquals("Customer not found.", exception.getMessage());
        verify(customerRepository, times(0)).delete(any());
        verify(customerRepository, times(1)).findCustomerById(id);
    }
    @Test
    public void testLoginCustomer() {
        String email = "email";
        String password = "password";
        Customer customer = new Customer("password", "email", "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);


        //act
        Boolean loggedInCustomer = customerService.loginCustomer(email, password);

        //assert
        assertNotNull(loggedInCustomer);
        assertTrue(loggedInCustomer);
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }
    @Test
    public void testLoginCustomerNullEmail() {
        String email = null;
        String password = "password";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.loginCustomer(email, password);
        });

        // Assert
        assertEquals("Customer email cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).findCustomerByEmail(email);
    }
    @Test
    public void testLoginCustomerNullPassword() {
        String email = "email";
        String password = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.loginCustomer(email, password);
        });

        // Assert
        assertEquals("Customer password cannot be empty.", exception.getMessage());
        verify(customerRepository, times(0)).findCustomerByEmail(email);
    }
    @Test
    public void testLoginCustomerNonExistentEmail() {
        String email = "nonexistentemail";
        String password = "password";
        when(customerRepository.findCustomerByEmail(email)).thenReturn(null);

        // Act
        Boolean loggedInCustomer = customerService.loginCustomer(email, password);

        // Assert
        assertNotNull(loggedInCustomer);
        assertFalse(loggedInCustomer);
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }
    @Test
    public void testLoginCustomerIncorrectPassword() {
        String email = "email";
        String password = "password";
        Customer customer = new Customer("password1", email, "username", 123456789, new Date(2021, 12, 31), "address");
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);

        // Act
        Boolean loggedInCustomer = customerService.loginCustomer(email, password);

        // Assert
        assertNotNull(loggedInCustomer);
        assertFalse(loggedInCustomer);
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }
}
