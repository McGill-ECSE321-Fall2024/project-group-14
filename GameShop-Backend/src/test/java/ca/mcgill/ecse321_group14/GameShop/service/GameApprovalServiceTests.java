package ca.mcgill.ecse321_group14.GameShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest.Status;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameApprovalRequestRepository;

@SpringBootTest
public class GameApprovalServiceTests {

    @Mock
    private GameApprovalRequestRepository gameApprovalRequestRepository;

    @Mock
    private Employee employeeMock;

    @Mock
    private Manager managerMock;

    @InjectMocks
    private GameApprovalService gameApprovalService;

    public GameApprovalServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGameApprovalRequest() {
        // Given
        String name = "Test Game";
        String description = "A test game for approval";
        String category = "Action";
        String picture = "image.png";
        Status status = Status.PendingApproval;

        when(gameApprovalRequestRepository.save(any(GameApprovalRequest.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        GameApprovalRequest gameApprovalRequest = gameApprovalService.createGameApprovalRequest(
                name, description, category, picture, status, employeeMock, managerMock);

        // Assert
        assertNotNull(gameApprovalRequest);
        assertEquals(name, gameApprovalRequest.getName());
        assertEquals(description, gameApprovalRequest.getDescription());
        assertEquals(category, gameApprovalRequest.getCategory());
        assertEquals(picture, gameApprovalRequest.getPicture());
        assertEquals(status, gameApprovalRequest.getStatus());
        assertEquals(employeeMock, gameApprovalRequest.getRequestCreator());
        assertEquals(managerMock, gameApprovalRequest.getRequestApprover());
        verify(gameApprovalRequestRepository, times(1)).save(any(GameApprovalRequest.class));
    }

    @Test
    public void testGetGameApprovalRequest() {
        // Given
        int id = 1;
        GameApprovalRequest gameApprovalRequest = new GameApprovalRequest("Test Game", "Description", "Action", "image.png", Status.PendingApproval, employeeMock, managerMock);
        when(gameApprovalRequestRepository.findGameApprovalRequestById(id)).thenReturn(gameApprovalRequest);

        // Act
        GameApprovalRequest foundRequest = gameApprovalService.getGameApprovalRequest(id);

        // Assert
        assertNotNull(foundRequest);
        assertEquals(gameApprovalRequest, foundRequest);
        verify(gameApprovalRequestRepository, times(1)).findGameApprovalRequestById(id);
    }

    @Test
    public void testUpdateGameApprovalRequest() {
        // Given
        int id = 1;
        GameApprovalRequest existingRequest = new GameApprovalRequest("Old Game", "Old Description", "Old Category", "old_image.png", Status.PendingApproval, employeeMock, managerMock);
        when(gameApprovalRequestRepository.findGameApprovalRequestById(id)).thenReturn(existingRequest);
        when(gameApprovalRequestRepository.save(any(GameApprovalRequest.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // New data for update
        String newName = "Updated Game";
        String newDescription = "Updated Description";
        String newCategory = "Updated Category";
        String newPicture = "updated_image.png";
        Status newStatus = Status.Approved;

        // Act
        GameApprovalRequest updatedRequest = gameApprovalService.updateGameApprovalRequest(id, newName, newDescription, newCategory, newPicture, newStatus, employeeMock, managerMock);

        // Assert
        assertNotNull(updatedRequest);
        assertEquals(newName, updatedRequest.getName());
        assertEquals(newDescription, updatedRequest.getDescription());
        assertEquals(newCategory, updatedRequest.getCategory());
        assertEquals(newPicture, updatedRequest.getPicture());
        assertEquals(newStatus, updatedRequest.getStatus());
        assertEquals(employeeMock, updatedRequest.getRequestCreator());
        assertEquals(managerMock, updatedRequest.getRequestApprover());
        verify(gameApprovalRequestRepository, times(1)).save(existingRequest);
    }

    @Test
    public void testDeleteGameApprovalRequest() {
        // Given
        int id = 1;
        GameApprovalRequest gameApprovalRequest = new GameApprovalRequest("Test Game", "Description", "Action", "image.png", Status.PendingApproval, employeeMock, managerMock);
        when(gameApprovalRequestRepository.findGameApprovalRequestById(id)).thenReturn(gameApprovalRequest);

        // Act
        gameApprovalService.deleteGameApprovalRequest(id);

        // Assert
        verify(gameApprovalRequestRepository, times(1)).delete(gameApprovalRequest);
        verify(gameApprovalRequestRepository, times(1)).findGameApprovalRequestById(id);
    }

    // Add other tests as needed
}
