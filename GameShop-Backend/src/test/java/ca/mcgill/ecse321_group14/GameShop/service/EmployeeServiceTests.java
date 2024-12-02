package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class EmployeeServiceTests {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @SuppressWarnings("null")
    @Test
    public void testCreateEmployee() {
        String password = "password";
        String username = "username";
        String email = "email";

        when(employeeRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        //act
        Employee createdEmployee = employeeService.createEmployee(username, email, password);

        //assert
        assertNotNull(createdEmployee);
        assertEquals(username, createdEmployee.getUsername());
        assertEquals(email, createdEmployee.getEmail());
        assertEquals(password, createdEmployee.getPassword());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    public void testCreateEmployeeNullPassword(){
        String password = null;
        String username = "username";
        String email = "email";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(username, email, password);
        });

        //assert
        assertEquals("Employee password cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }
    @Test
    public void testCreatEmployeeNullUsername(){
        String password = "password";
        String username = null;
        String email = "email";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(username, email, password);
        });

        //assert
        assertEquals("Employee name cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }
    @Test
    public void testCreatEmployeeNullEmail(){
        String password = "password";
        String username = "username";
        String email = null;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(username, email, password);
        });

        //assert
        assertEquals("Employee email cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }
    @Test
    public void testFindEmployeeById() {
        int id = 1;
        Employee employee = new Employee("password", "email", "username");
        when(employeeRepository.findEmployeeById(id)).thenReturn(employee);

        //act
        Employee validEmployee = employeeService.getEmployeeById(id);

        //assert
        assertNotNull(validEmployee);
        assertEquals(employee, validEmployee);
        assertEquals(employee.getId(), validEmployee.getId());
        assertEquals(employee.getPassword(), validEmployee.getPassword());
        assertEquals(employee.getEmail(), validEmployee.getEmail());
        assertEquals(employee.getUsername(), validEmployee.getUsername());
        verify(employeeRepository, times(1)).findEmployeeById(id);
    }
    @Test
    public void testFindEmployeeByNonExistentId(){
        int id = 1;
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeById(id);
        });

        //assert
        assertEquals("Employee not found.", exception.getMessage());
        verify(employeeRepository, times(1)).findEmployeeById(id);
    }

    @Test
    public void testFindEmployeeByNullId(){
        int id = 0;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeById(id);
        });

        //assert
        assertEquals("Employee id cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).findEmployeeById(id);
    }
    @Test
    public void testFindEmployeeByEmail() {
        String email = "email";
        Employee employee = new Employee("password", email, "username");
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);

        //act
        Employee validEmployee = employeeService.getEmployeeByEmail(email);

        //assert
        assertNotNull(validEmployee);
        assertEquals(employee, validEmployee);
        assertEquals(employee.getId(), validEmployee.getId());
        assertEquals(employee.getPassword(), validEmployee.getPassword());
        assertEquals(employee.getEmail(), validEmployee.getEmail());
        assertEquals(employee.getUsername(), validEmployee.getUsername());
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }
    @Test
    public void testFindEmployeeByNonExistentEmail(){
        String email = "email";
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeByEmail(email);
        });

        //assert
        assertEquals("Employee not found.", exception.getMessage());
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }
    @Test
    public void testFindEmployeeByNullEmail(){
        String email = null;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeByEmail(email);
        });

        //assert
        assertEquals("Employee email cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).findEmployeeByEmail(email);
    }
    @Test
    public void testUpdateEmployee() {
        int id = 1;
        String password = "password";
        String username = "username";
        String email = "email";
        Employee employee = new Employee("password1", "email1", "username1");
        when(employeeRepository.findEmployeeById(id)).thenReturn(employee);
        when(employeeRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        //act
        Employee updatedEmployee = employeeService.updateEmployee(id, username, email, password);

        //assert
        assertNotNull(updatedEmployee);
        assertEquals(username, updatedEmployee.getUsername());
        assertEquals(email, updatedEmployee.getEmail());
        assertEquals(password, updatedEmployee.getPassword());
        verify(employeeRepository, times(1)).findEmployeeById(id);
        verify(employeeRepository, times(1)).save(any());
    }
    @Test
    public void testUpdateEmployeeNullPassword() {
        int id = 1;
        String password = null;
        String username = "username";
        String email = "email";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(id, username, email, password);
        });

        // Assert
        assertEquals("Employee password cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }
    @Test
    public void testUpdateEmployeeNullUsername() {
        int id = 1;
        String password = "password";
        String username = null;
        String email = "email";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(id, username, email, password);
        });

        // Assert
        assertEquals("Employee name cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }
    @Test
    public void testUpdateEmployeeNullEmail() {
        int id = 1;
        String password = "password";
        String username = "username";
        String email = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(id, username, email, password);
        });

        // Assert
        assertEquals("Employee email cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).save(any());
    }

    @Test
    public void testDeleteEmployee() {
        int id = 1;
        Employee employee = new Employee("password", "email", "username");
        when(employeeRepository.findEmployeeById(id)).thenReturn(employee);

        //act
        employeeService.deleteEmployee(id);

        //assert

        verify(employeeRepository, times(1)).findEmployeeById(id);
        verify(employeeRepository, times(1)).delete(employee);
    }
    @Test
    public void testDeleteEmployeeNonExistentId(){
        int id = 1;
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.deleteEmployee(id);
        });

        //assert
        assertEquals("Employee not found.", exception.getMessage());
        verify(employeeRepository, times(1)).findEmployeeById(id);
        verify(employeeRepository, times(0)).delete(any());
    }
    @Test
    public void testDeleteEmployeeNullId(){
        int id = 0;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.deleteEmployee(id);
        });

        //assert
        assertEquals("Employee id cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).findEmployeeById(id);
        verify(employeeRepository, times(0)).delete(any());
    }
    @Test
    public void testEmployeeLogin() {
        String email = "email";
        String password = "password";
        Employee employee = new Employee(password, email, "username");
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);

        //act
        Boolean employeeLoggedIn = employeeService.loginEmployee(email, password);

        //assert
        assertNotNull(employeeLoggedIn);
        assertTrue(employeeLoggedIn);
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }
    @Test
    public void testEmployeeLoginNonExistentEmail(){
        String email = "email";
        String password = "password";
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.loginEmployee(email, password);
        });

        //assert
        assertEquals("Employee not found.", exception.getMessage());
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }
    @Test
    public void testEmployeeLoginNullEmail(){
        String email = null;
        String password = "password";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.loginEmployee(email, password);
        });

        //assert
        assertEquals("Employee email cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).findEmployeeByEmail(email);
    }
    @Test
    public void testEmployeeLoginNullPassword(){
        String email = "email";
        String password = null;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.loginEmployee(email, password);
        });

        //assert
        assertEquals("Employee password cannot be empty.", exception.getMessage());
        verify(employeeRepository, times(0)).findEmployeeByEmail(email);
    }
    @Test
    public void testEmployeeLoginIncorrectPassword(){
        String email = "email";
        String password = "password";
        Employee employee = new Employee("password1", email, "username");
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);

        //act
        Boolean employeeLoggedIn = employeeService.loginEmployee(email, password);

        //assert
        assertNotNull(employeeLoggedIn);
        assertFalse(employeeLoggedIn);
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }
    @Test
    public void testEmployeeLoginIncorrectEmail(){
        String email = "email";
        String password = "password";
        Employee employee = new Employee(password, "email1", "username");
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);

        //act
        Boolean employeeLoggedIn = employeeService.loginEmployee(email, password);

        //assert
        assertNotNull(employeeLoggedIn);
        assertFalse(employeeLoggedIn);
        verify(employeeRepository, times(1)).findEmployeeByEmail(email);
    }







}
