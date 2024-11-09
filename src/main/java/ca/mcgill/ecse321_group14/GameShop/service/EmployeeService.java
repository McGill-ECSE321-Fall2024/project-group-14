package ca.mcgill.ecse321_group14.GameShop.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.repository.EmployeeRepository;
import jakarta.transaction.Transactional;


@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee createEmployee(String name, String email, String password) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Employee email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Employee password cannot be empty.");
        }
        Employee employee = new Employee(name, email, password);
        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public Employee getEmployeeByEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Employee email cannot be empty.");
        }
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        return employee;
    }

    @Transactional
    public Employee getEmployeeById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Employee id cannot be empty.");
        }
        Employee employee = employeeRepository.findEmployeeById(id);
        return employee;
    }

    @Transactional
    public Employee updateEmployee(int id, String name, String email, String password) {
        if (id == 0) {
            throw new IllegalArgumentException("Employee id cannot be empty.");
        }
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Employee email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Employee password cannot be empty.");
        }
        Employee employee = employeeRepository.findEmployeeById(id);
        employee.setUsername(name);
        employee.setEmail(email);
        employee.setPassword(password);
        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public void deleteEmployee(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Employee id cannot be empty.");
        }
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        employeeRepository.delete(employee);
    }

    @Transactional
    public boolean loginEmployee(String email, String password) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Employee email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Employee password cannot be empty.");
        }
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        return employee.getPassword().equals(password);
    }

    @Transactional
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }
}
