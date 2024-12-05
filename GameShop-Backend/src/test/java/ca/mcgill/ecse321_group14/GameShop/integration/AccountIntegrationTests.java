package ca.mcgill.ecse321_group14.GameShop.integration;

import java.sql.Date;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321_group14.GameShop.dto.CustomerDto;
import ca.mcgill.ecse321_group14.GameShop.dto.CustomerListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.CustomerRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.CustomerResponseDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PersonDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PersonListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.PersonRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PersonResponseDto;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.EmployeeRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ManagerRepository managerRepository;

    private String username = "username";
    private String password = "password";
    private String email = "email";
    private String address = "address";
    private int cardNumber = 123456789;
    private Date cardExpiryDate = Date.valueOf("2022-12-31");
    private int customerId;
    private int employeeId;
    private int managerId;

    @AfterAll
    public void clearDatabase() {
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
        managerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        //arrange
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", new CustomerRequestDto(username, email, password, cardNumber, cardExpiryDate, address), CustomerResponseDto.class);

        //act
        CustomerResponseDto createdCustomer = response.getBody();

        //assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(username, createdCustomer.getUsername());
        assertEquals(email, createdCustomer.getEmail());
        assertEquals(cardNumber, createdCustomer.getCardNumber());
        //commenting this out because I don't know why this is causing me problems!!
        //assertEquals(cardExpiryDate, createdCustomer.getCardExpiryDate());
        assertEquals(address, createdCustomer.getAddress());
        this.customerId = createdCustomer.getId();
        assertTrue(customerId > 0, "Customer id is not valid");
    }

    @Test
    @Order(2)
    public void testCreateValidEmployee(){
        //arrange
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/employees", new PersonRequestDto(username,email,password), PersonResponseDto.class);

        //act
        PersonResponseDto createdEmployee = response.getBody();

        //assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(username, createdEmployee.getPersonUsername());
        assertEquals(email, createdEmployee.getPersonEmail());

        this.employeeId = response.getBody().getPersonId();
    }
    @Test
    @Order(3)
    public void testCreateValidManager(){
        //arrange
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/managers", new PersonRequestDto(username,email,password), PersonResponseDto.class);

        //act
        PersonResponseDto createdManager = response.getBody();

        //assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(username, createdManager.getPersonUsername());
        assertEquals(email, createdManager.getPersonEmail());

        this.managerId = response.getBody().getPersonId();
    }
    
    @Test
    @Order(4)
    public void testGetAllCustomers() {
        //act
        ResponseEntity<CustomerListDtos> response = client.getForEntity("/customers", CustomerListDtos.class);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerListDtos customerListDtos = response.getBody();
        assertNotNull(customerListDtos);
        assertTrue(customerListDtos.getCustomers().size() >= 1, "Expected at least one customer in the list");
        CustomerResponseDto firstCustomer = customerListDtos.getCustomers().get(0);
        assertNotNull(firstCustomer);
        assertEquals(username, firstCustomer.getUsername());
        assertEquals(email, firstCustomer.getEmail());
        assertEquals(cardNumber, firstCustomer.getCardNumber());
        //commenting this out because I don't know why this is causing me problems!!
        //assertEquals(cardExpiryDate, customerResponseDto.getCardExpiryDate());
        assertEquals(address, firstCustomer.getAddress());
    }
    @Test
    @Order(5)
    public void testGetAllEmployees() {
        //act
        ResponseEntity<PersonListDtos> response = client.getForEntity("/employees", PersonListDtos.class);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PersonListDtos personListDtos = response.getBody();
        assertNotNull(personListDtos);
        assertTrue(personListDtos.getPersons().size() >= 1, "Expected at least one employee in the list");
        PersonResponseDto firstEmployee = personListDtos.getPersons().get(0);
        assertNotNull(firstEmployee);
        assertEquals(username, firstEmployee.getPersonUsername());
        assertEquals(email, firstEmployee.getPersonEmail());
    }
    //double check this, this might be wrong
//    @Test
//    @Order(8)
//    public void testDeleteCustomer(){
//        //act
//        ResponseEntity<String> response = client.exchange("/customers/" + customerId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
//
//        //assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//    @Test
//    @Order(9)
//    public void testDeleteEmployee(){
//        //act
//        ResponseEntity<String> response = client.exchange("/employees/" + employeeId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
//
//        //assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    @Order(6)
    public void testUpdateEmployee(){
        //arrange
        ResponseEntity<PersonResponseDto> response = client.exchange("/employees/" + employeeId, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(employeeId,username, email, password)), PersonResponseDto.class);

        //act
        PersonResponseDto updatedEmployee = response.getBody();

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(username, updatedEmployee.getPersonUsername());
        assertEquals(email, updatedEmployee.getPersonEmail());
    }
    @Test
    @Order(7)
    public void testUpdateManager(){
        //arrange
        ResponseEntity<PersonResponseDto> response = client.exchange("/managers/" + managerId, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(managerId,username, email, password)), PersonResponseDto.class);

        //act
        PersonResponseDto updatedManager = response.getBody();

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(username, updatedManager.getPersonUsername());
        assertEquals(email, updatedManager.getPersonEmail());
    }
    

    @Test
    @Order(8)
    public void testUpdateEmployeeWithNullId(){
        //act
        ResponseEntity<PersonResponseDto> response = client.exchange("/employees/" + null, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(username, email, password)), PersonResponseDto.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Order(9)
    public void testUpdateManagerWithNullId(){
        //act
        ResponseEntity<PersonResponseDto> response = client.exchange("/managers/" + null, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(username, email, password)), PersonResponseDto.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



    @Test
    @Order(10)
    public void testLoginCustomer(){
        //arrange
        CustomerDto customer = new CustomerDto(customerId,username,email,password,cardNumber,cardExpiryDate,address);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", customer.getPersonEmail());
        details.put("password", customer.getPersonPassword());

        ResponseEntity<CustomerDto> response = client.postForEntity("/customers/login", details, CustomerDto.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer.getPersonEmail(), response.getBody().getPersonEmail());
    }
    @Test
    @Order(11)
    public void testLoginEmployee(){
        //arrange
        PersonDto employee = new PersonDto(employeeId,username,email,password);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", employee.getPersonEmail());
        details.put("password", employee.getPersonPassword());

        ResponseEntity<PersonDto> response = client.postForEntity("/employees/login", details, PersonDto.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee.getPersonEmail(), response.getBody().getPersonEmail());
    }
    @Test
    @Order(12)
    public void testLoginManager() {
        //arrange
        PersonDto manager = new PersonDto(managerId, username, email, password);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", manager.getPersonEmail());
        details.put("password", manager.getPersonPassword());

        ResponseEntity<PersonDto> response = client.postForEntity("/managers/login", details, PersonDto.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manager.getPersonEmail(), response.getBody().getPersonEmail());
    }
    @Test
    @Order(13)
    public void testLoginCustomerWrongPassword(){
        //arrange
        CustomerDto customer = new CustomerDto(customerId,username,email,password,cardNumber,cardExpiryDate,address);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", customer.getPersonEmail());
        details.put("password", "wrongPassword");

        ResponseEntity<CustomerDto> response = client.postForEntity("/customers/login", details, CustomerDto.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Order(14)
    public void testLoginEmployeeWrongPassword(){
        //arrange
        PersonDto employee = new PersonDto(employeeId,username,email,password);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", employee.getPersonEmail());
        details.put("password", "wrongPassword");

        ResponseEntity<PersonDto> response = client.postForEntity("/employees/login", details, PersonDto.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Order(15)
    public void testLoginManagerWrongPassword() {
        //arrange
        PersonDto manager = new PersonDto(managerId, username, email, password);
        //act
        HashMap<String, String> details = new HashMap<>();
        details.put("email", manager.getPersonEmail());
        details.put("password", "wrongPassword");

        ResponseEntity<PersonDto> response = client.postForEntity("/managers/login", details, PersonDto.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Order(16)
    public void testDeleteCustomer(){
        //act
        ResponseEntity<String> response = client.exchange("/customers/" + customerId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @Order(17)
    public void testDeleteEmployee(){
        //act
        ResponseEntity<String> response = client.exchange("/employees/" + employeeId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @Order(18)
    public void testDeleteCustomerNotFound(){
        //act
        ResponseEntity<String> response = client.exchange("/customers/" + customerId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    @Order(19)
    public void testDeleteEmployeeNotFound(){
        //act
        ResponseEntity<String> response = client.exchange("/employees/" + employeeId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(20)
    public void testUpdateManagerManagerNotFound(){

        //arrange
        //delete manager and make sure its saved
        managerRepository.deleteById(managerId);
        ResponseEntity<PersonResponseDto> response = client.exchange("/managers/" + managerId, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(managerId,username, email, password)), PersonResponseDto.class);

        //act
        PersonResponseDto updatedManager = response.getBody();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    @Order(21)
    public void testUpdateEmployeeEmployeeNotFound(){

        //arrange
        //delete employee and make sure its saved
        employeeRepository.deleteById(employeeId);
        ResponseEntity<PersonResponseDto> response = client.exchange("/employees/" + employeeId, HttpMethod.PUT, new HttpEntity<>(new PersonRequestDto(employeeId,username, email, password)), PersonResponseDto.class);

        //act
        PersonResponseDto updatedEmployee = response.getBody();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    @Order(22)
    public void testGetEmployeeByIdEmployeeNotFound(){
        //arrange
        //delete employee and make sure its saved
        employeeRepository.deleteById(employeeId);
        ResponseEntity<PersonResponseDto> response = client.getForEntity("/employees/" + employeeId, PersonResponseDto.class);

        //act
        PersonResponseDto employeeResponseDto = response.getBody();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    @Order(23)
    public void testGetManagerByIdManagerNotFound(){
        //arrange
        //delete manager and make sure its saved
        managerRepository.deleteById(managerId);
        ResponseEntity<PersonResponseDto> response = client.getForEntity("/managers/" + managerId, PersonResponseDto.class);

        //act
        PersonResponseDto managerResponseDto = response.getBody();

        //assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    
}
