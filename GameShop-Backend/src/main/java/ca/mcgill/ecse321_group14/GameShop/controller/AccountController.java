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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            if (id == 0){
                return new ResponseEntity<>(new CustomerDto(), HttpStatus.BAD_REQUEST);
            }
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
            if (id == 0){
                return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
            }
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
            if (id == 0){
                return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
            }
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
                    customerToCreate.getPassword(),
                    customerToCreate.getEmail(),
                    customerToCreate.getUsername(),
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
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String pid, @RequestBody CustomerRequestDto customerdto) {
        int id;
        try {
            id = Integer.parseInt(pid);
            if (id == 0){
                return new ResponseEntity<>(new CustomerResponseDto(), HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(new CustomerResponseDto(), HttpStatus.BAD_REQUEST);
        }
        try{
        Customer customerToUpdate = customerService.getCustomerById(id);
        customerToUpdate = customerService.updateCustomer(
                customerdto.getId(),
                customerdto.getPassword(),
                customerdto.getEmail(),
                customerdto.getUsername(),
                customerdto.getCardNumber(),
                customerdto.getCardExpiryDate(),
                customerdto.getAddress()
        );
        return new ResponseEntity<>(new CustomerResponseDto(customerToUpdate), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new CustomerResponseDto(), HttpStatus.NOT_FOUND);
        }

   }

   @PutMapping("/employees/{pid}")
   public ResponseEntity<PersonDto> updateEmployee(@PathVariable String pid, @RequestBody PersonDto employeedto) {
       int id;
       try {
           id = Integer.parseInt(pid);
           if (id == 0){
               return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
           }
       } catch (NumberFormatException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }
       try{
           Employee employeeToUpdate = employeeService.getEmployeeById(id);
           employeeToUpdate = employeeService.updateEmployee(
                   employeedto.getPersonId(),
                   employeedto.getPersonUsername(),
                   employeedto.getPersonEmail(),
                   employeedto.getPersonPassword()
           );
           return new ResponseEntity<>(new PersonDto(employeeToUpdate), HttpStatus.OK);
       }
       catch (Exception e){
           return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
       }

   }

   @PutMapping("/managers/{pid}")
   public ResponseEntity<PersonDto> updateManager(@PathVariable String pid, @RequestBody PersonDto managerdto) {
       int id;
       try {
           id = Integer.parseInt(pid);
           if (id == 0){
               return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
           }
       } catch (NumberFormatException e) {
           return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
       }
       try {
           Manager managerToUpdate = managerService.getManagerById(id);
           managerToUpdate = managerService.updateManager(
                   managerdto.getPersonId(),
                   managerdto.getPersonUsername(),
                   managerdto.getPersonEmail(),
                   managerdto.getPersonPassword()
           );
           return new ResponseEntity<>(new PersonDto(managerToUpdate), HttpStatus.OK);
       }
         catch (Exception e){
              return new ResponseEntity<>(new PersonDto(), HttpStatus.NOT_FOUND);
         }


   }

   @DeleteMapping("/customers/{pid}")
   public ResponseEntity<?> deleteCustomer(@PathVariable String pid) throws Exception{
       int id;
       try{
           id = Integer.parseInt(pid);
           if (id == 0){
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
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
           if (id == 0){
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
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
    @PostMapping("/managers/login")
    public ResponseEntity<PersonDto> loginManager(@RequestBody Map<String, String> details) {
        Manager manager;
        String email = details.get("email");
        String password = details.get("password");
        if (managerService.loginManager(email, password)){
            manager = managerService.getManagerByEmail(email);
            return new ResponseEntity<>(new PersonDto(manager), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/employees/login")
    public ResponseEntity<PersonDto> loginEmployee(@RequestBody Map<String, String> details) {
        Employee employee;
        String email = details.get("email");
        String password = details.get("password");
        if (employeeService.loginEmployee(email, password)){
            employee = employeeService.getEmployeeByEmail(email);
            return new ResponseEntity<>(new PersonDto(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new PersonDto(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/customers/login")
    public ResponseEntity<CustomerDto> loginCustomer(@RequestBody Map<String, String> details) {
        Customer customer;
        String email = details.get("email");
        String password = details.get("password");
        if (customerService.loginCustomer(email, password)){
            customer = customerService.getCustomerByEmail(email);
            return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomerDto(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/customers")
    public CustomerListDtos getAllCustomers() {
        List<CustomerResponseDto> dtos = new ArrayList<CustomerResponseDto>();
        for (Customer customer : customerService.getAllCustomers()) {
            dtos.add(new CustomerResponseDto(customer));
        }
        return new CustomerListDtos(dtos);
    }
    @GetMapping("/employees")
    public PersonListDtos getAllEmployees() {
        List<PersonResponseDto> dtos = new ArrayList<PersonResponseDto>();
        for (Employee employee : employeeService.getAllEmployees()) {
            dtos.add(new PersonResponseDto(employee));
        }
        return new PersonListDtos(dtos);
    }












}
