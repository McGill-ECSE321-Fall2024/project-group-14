package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;

@SpringBootTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadEmployee() {
        // Arrange
        Employee employee = new Employee("password", "email", "username"); // create a new employee
        employee = repo.save(employee); // save the employee to the database
        int id = employee.getId(); // get the id of the employee

        // Act
        Employee readEmployee = repo.findEmployeeById(id); // read the employee from the database

        // Assert
        assertNotNull(readEmployee);
        assertEquals(employee.getId(), readEmployee.getId());
        assertEquals(employee.getPassword(), readEmployee.getPassword());
        assertEquals(employee.getEmail(), readEmployee.getEmail());
        assertEquals(employee.getUsername(), readEmployee.getUsername());

    }
}
