package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.*;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Person;
import ca.mcgill.ecse321_group14.GameShop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321_group14.GameShop.service.CustomerService;
import ca.mcgill.ecse321_group14.GameShop.service.ManagerService;

import java.sql.Date;

@RestController
public class AccountController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ManagerService managerService;

    @GetMapping("/customers/{pid}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable String pid) throws Exception{
        int id;
        try{
            id = Integer.parseInt(pid);
        } catch (NumberFormatException e){
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.BAD_REQUEST);
        }
        Customer customer;
        try {
            customer = customerService.getCustomerById(id);
            return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/employees/{pid}")
    public ResponseEntity<PersonDto> findEmployeeById(@PathVariable String pid) throws Exception{
        int id;
        try{
            id = Integer.parseInt(pid);
        } catch (NumberFormatException e){
            return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
        }
        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(new PersonDto(employee), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/managers/{pid}")
    public ResponseEntity<PersonDto> findManagerById(@PathVariable String pid) throws Exception{
        int id;
        try{
            id = Integer.parseInt(pid);
        } catch (NumberFormatException e){
            return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
        }
        Manager manager;
        try {
            manager = managerService.getManagerById(id);
            return new ResponseEntity<>(new PersonDto(manager), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerToCreate) {
        try {
            Customer createdCustomer = customerService.createCustomer(
                    customerToCreate.getUsername(),
                    customerToCreate.getEmail(),
                    customerToCreate.getPassword(),
                    customerToCreate.getCardNumber(),
                    customerToCreate.getCardExpiryDate(),
                    customerToCreate.getAddress()
            );
            return new ResponseEntity<>(new CustomerResponseDto(createdCustomer), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new CustomerResponseDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<PersonResponseDto> createEmployee(@RequestBody PersonRequestDto employeeToCreate) {
        try {
            Employee createdEmployee = employeeService.createEmployee(
                    employeeToCreate.getPersonUsername(),
                    employeeToCreate.getPersonEmail(),
                    employeeToCreate.getPersonPassword()
            );
            return new ResponseEntity<>(new PersonResponseDto(createdEmployee), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new PersonResponseDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/managers")
    public ResponseEntity<PersonResponseDto> createManager(@RequestBody PersonRequestDto managerToCreate) {
        try {
            Manager createdManager = managerService.createManager(
                    managerToCreate.getPersonUsername(),
                    managerToCreate.getPersonEmail(),
                    managerToCreate.getPersonPassword()
            );
            return new ResponseEntity<>(new PersonResponseDto(createdManager), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new PersonResponseDto(), HttpStatus.BAD_REQUEST);
        }
    }

   @PutMapping("/customers/{pid}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable String pid, @RequestBody CustomerDto customerdto) {
        int id;
        try {
            id = Integer.parseInt(pid);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.BAD_REQUEST);
        }
        Customer customerToUpdate = customerService.getCustomerById(id);
        if (customerToUpdate == null) {
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.NOT_FOUND);
        }
        try {
            customerToUpdate = customerService.updateCustomer(
                    customerdto.getPersonUsername(),
                    customerdto.getPersonEmail(),
                    customerdto.getPersonPassword(),
                    customerdto.getCardNumber(),
                    customerdto.getCardExpiryDate(),
                    customerdto.getAddress()
            );
            return new ResponseEntity<>(new CustomerDto(customerToUpdate), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.BAD_REQUEST);
        }

   }

   @PutMapping("/employees/{pid}")
   public ResponseEntity<PersonDto> updateEmployee(@PathVariable String pid, @RequestBody PersonDto employeedto) {
       int id;
       try {
           id = Integer.parseInt(pid);
       } catch (NumberFormatException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }
       Employee employeeToUpdate = employeeService.getEmployeeById(id);
       if (employeeToUpdate == null) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
       }
       try {
           employeeToUpdate = employeeService.updateEmployee(
                   employeedto.getPersonId(),
                   employeedto.getPersonUsername(),
                   employeedto.getPersonEmail(),
                   employeedto.getPersonPassword()
           );
           return new ResponseEntity<>(new PersonDto(employeeToUpdate), HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }

   }

   @PutMapping("/managers/{pid}")
   public ResponseEntity<PersonDto> updateManager(@PathVariable String pid, @RequestBody PersonDto managerdto) {
       int id;
       try {
           id = Integer.parseInt(pid);
       } catch (NumberFormatException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }
       Manager managerToUpdate = managerService.getManagerById(id);
       if (managerToUpdate == null) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
       }
       try {
           managerToUpdate = managerService.updateManager(
                   managerdto.getPersonId(),
                   managerdto.getPersonUsername(),
                   managerdto.getPersonEmail(),
                   managerdto.getPersonPassword()
           );
           return new ResponseEntity<>(new PersonDto(managerToUpdate), HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }

   }

   @DeleteMapping("/customers/{pid}")
   public ResponseEntity<?> deleteCustomer(@PathVariable String pid) throws Exception{
       int id;
       try{
           id = Integer.parseInt(pid);
       } catch (NumberFormatException e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
       try {
           customerService.deleteCustomer(id);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
   }
   @DeleteMapping("/employees/{pid}")
   public ResponseEntity<?> deleteEmployee(@PathVariable String pid) throws Exception{
       int id;
       try{
           id = Integer.parseInt(pid);
       } catch (NumberFormatException e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
       try {
           employeeService.deleteEmployee(id);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
   }
    @PostMapping("/managers/login/{email}/{password}")
    public ResponseEntity<PersonDto> loginManager(@PathVariable String email, @PathVariable String password) {
        Manager manager;
        if (managerService.loginManager(email, password)){
            manager = managerService.getManagerByEmail(email);
            return new ResponseEntity<>(new PersonDto(manager), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PersonDto(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/employees/login/{email}/{password}")
    public ResponseEntity<PersonDto> loginEmployee(@PathVariable String email, @PathVariable String password) {
        Employee employee;
        if (employeeService.loginEmployee(email, password)){
            employee = employeeService.getEmployeeByEmail(email);
            return new ResponseEntity<>(new PersonDto(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PersonDto(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/customers/login/{email}/{password}")
    public ResponseEntity<CustomerDto> loginCustomer(@PathVariable String email, @PathVariable String password) {
        Customer customer;
        if (customerService.loginCustomer(email, password)){
            customer = customerService.getCustomerByEmail(email);
            return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.UNAUTHORIZED);
        }
    }










}
