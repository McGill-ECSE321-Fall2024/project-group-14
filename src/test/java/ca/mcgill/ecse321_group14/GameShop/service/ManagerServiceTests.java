package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ManagerServiceTests {
    @Mock
    ManagerRepository managerRepository;
    @InjectMocks
    ManagerService managerService;
    @SuppressWarnings("null")
    @Test
    public void testCreateManager() {
        String password = "password";
        String email = "email";
        String username = "username";
        Manager manager = new Manager(password, email, username);
        when(managerRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        Manager createdManager = managerService.createManager(username, email, password);

        // Assert
        assertNotNull(createdManager);
        assertEquals(password, createdManager.getPassword());
        assertEquals(email, createdManager.getEmail());
        assertEquals(username, createdManager.getUsername());
        verify(managerRepository, times(1)).save(any());
    }
    @Test
    public void testCreateManagerNullPassword() {
        String password = null;
        String email = "email";
        String username = "username";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.createManager(username, email, password);
        });

        // Assert
        assertEquals("Manager password cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }
    @Test
    public void testCreateManagerNullUsername() {
        String password = "password";
        String email = "email";
        String username = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.createManager(username, email, password);
        });

        // Assert
        assertEquals("Manager name cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }
    @Test
    public void testCreateManagerNullEmail() {
        String password = "password";
        String email = null;
        String username = "username";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.createManager(username, email, password);
        });

        // Assert
        assertEquals("Manager email cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateManager() {
        int id = 1;
        String password = "password";
        String username = "username";
        String email = "email";
        Manager manager = new Manager("password1", "email1", "username1");
        when(managerRepository.findManagerById(id)).thenReturn(manager);
        when(managerRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        Manager updatedManager = managerService.updateManager(id, username, email, password);

        // Assert
        assertNotNull(updatedManager);
        assertEquals(username, updatedManager.getUsername());
        assertEquals(email, updatedManager.getEmail());
        assertEquals(password, updatedManager.getPassword());
        verify(managerRepository, times(1)).findManagerById(id);
        verify(managerRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateManagerNullPassword() {
        int id = 1;
        String password = null;
        String username = "username";
        String email = "email";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.updateManager(id, username, email, password);
        });

        // Assert
        assertEquals("Manager password cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateManagerNullUsername() {
        int id = 1;
        String password = "password";
        String username = null;
        String email = "email";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.updateManager(id, username, email, password);
        });

        // Assert
        assertEquals("Manager name cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateManagerNullEmail() {
        int id = 1;
        String password = "password";
        String username = "username";
        String email = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.updateManager(id, username, email, password);
        });

        // Assert
        assertEquals("Manager email cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }
    @Test
    public void testUpdateManagerNullId() {
        int id = 0;
        String password = "password";
        String username = "username";
        String email = "email";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.updateManager(id, username, email, password);
        });

        // Assert
        assertEquals("Manager id cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).save(any());
    }
    @Test
    public void testGetManagerById() {
        int id = 1;
        Manager manager = new Manager("password", "email", "username");
        when(managerRepository.findManagerById(id)).thenReturn(manager);

        // Act
        Manager foundManager = managerService.getManagerById(id);

        // Assert
        assertNotNull(foundManager);
        assertEquals(manager, foundManager);
        verify(managerRepository, times(1)).findManagerById(id);
    }
    @Test
    public void testGetManagerByIdNullId() {
        int id = 0;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.getManagerById(id);
        });

        // Assert
        assertEquals("Manager id cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).findManagerById(id);
    }
    @Test
    public void testGetManagerByNonExistentId(){
        int id = 1;
        when(managerRepository.findManagerById(id)).thenReturn(null);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.getManagerById(id);
        });

        // Assert
        assertEquals("Manager not found.", exception.getMessage());
        verify(managerRepository, times(1)).findManagerById(id);
    }
    @Test
    public void testGetManagerByEmail() {
        String email = "email";
        Manager manager = new Manager("password", email, "username");
        when(managerRepository.findManagerByEmail(email)).thenReturn(manager);

        // Act
        Manager foundManager = managerService.getManagerByEmail(email);

        // Assert
        assertNotNull(foundManager);
        assertEquals(manager, foundManager);
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }
    @Test
    public void testGetManagerByEmailNullEmail() {
        String email = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.getManagerByEmail(email);
        });

        // Assert
        assertEquals("Manager email cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).findManagerByEmail(email);
    }
    @Test
    public void testGetManagerByNonExistentEmail(){
        String email = "email";
        when(managerRepository.findManagerByEmail(email)).thenReturn(null);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.getManagerByEmail(email);
        });

        // Assert
        assertEquals("Manager not found.", exception.getMessage());
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }
    @Test
    public void testManagerLogin() {
        String email = "email";
        String password = "password";
        Manager manager = new Manager(password, email, "username");
        when(managerRepository.findManagerByEmail(email)).thenReturn(manager);

        // Act
        Boolean managerLoggedIn = managerService.loginManager(email, password);

        // Assert
        assertNotNull(managerLoggedIn);
        assertTrue(managerLoggedIn);
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }

    @Test
    public void testManagerLoginNonExistentEmail() {
        String email = "email";
        String password = "password";
        when(managerRepository.findManagerByEmail(email)).thenReturn(null);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.loginManager(email, password);
        });

        // Assert
        assertEquals("Manager not found.", exception.getMessage());
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }

    @Test
    public void testManagerLoginNullEmail() {
        String email = null;
        String password = "password";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.loginManager(email, password);
        });

        // Assert
        assertEquals("Manager email cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).findManagerByEmail(email);
    }

    @Test
    public void testManagerLoginNullPassword() {
        String email = "email";
        String password = null;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            managerService.loginManager(email, password);
        });

        // Assert
        assertEquals("Manager password cannot be empty.", exception.getMessage());
        verify(managerRepository, times(0)).findManagerByEmail(email);
    }

    @Test
    public void testManagerLoginIncorrectPassword() {
        String email = "email";
        String password = "password";
        Manager manager = new Manager("password1", email, "username");
        when(managerRepository.findManagerByEmail(email)).thenReturn(manager);

        // Act
        Boolean managerLoggedIn = managerService.loginManager(email, password);

        // Assert
        assertNotNull(managerLoggedIn);
        assertFalse(managerLoggedIn);
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }
    @Test
    public void testManagerLoginIncorrectEmail(){
        String email = "email";
        String password = "password";
        Manager manager = new Manager(password, "email1", "username");
        when(managerRepository.findManagerByEmail(email)).thenReturn(manager);

        // Act
        Boolean managerLoggedIn = managerService.loginManager(email, password);

        // Assert
        assertNotNull(managerLoggedIn);
        assertFalse(managerLoggedIn);
        verify(managerRepository, times(1)).findManagerByEmail(email);
    }
}
