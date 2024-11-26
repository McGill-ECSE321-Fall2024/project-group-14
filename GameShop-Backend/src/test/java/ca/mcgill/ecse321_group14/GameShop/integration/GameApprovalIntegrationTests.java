package ca.mcgill.ecse321_group14.GameShop.integration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321_group14.GameShop.dto.GameApprovalRequestDto;
import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest.Status;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.EmployeeRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameApprovalRequestRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameApprovalIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameApprovalRequestRepository gameApprovalRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired 
    private GameRepository gameRepository;

    private int gameApprovalRequestId;
    private int employeeId;
    private int managerId;


    @BeforeAll
    public void setup(){
        Employee employee = employeeRepository.save(new Employee("John", "john123", "password"));
        employeeId = employee.getId();  // Capture ID after saving

        Manager manager = managerRepository.save(new Manager("Manager", "manager123", "password"));
        managerId = manager.getId();  // Capture ID after saving

        assertNotNull(employee);  // Assert that employee is saved
        assertNotNull(manager);   // Assert that manager is saved
        assertTrue(employeeRepository.existsById(employeeId));
        assertTrue(managerRepository.existsById(managerId));
    }

    @AfterAll
    public void clearDatabase(){
        gameApprovalRequestRepository.deleteAll();
        employeeRepository.deleteAll();
        managerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateGameApprovalRequest(){
        //Arrange
        GameApprovalRequestDto gameApprovalRequestDto = new GameApprovalRequestDto(
            "Test Game",
            "A test game for approval",
            "Action",
            "image.png",
            Status.PendingApproval,
            employeeId,
            managerId

        );

        //Act
        ResponseEntity<GameApprovalRequestDto> responseEntity = client.postForEntity("/gameapproval", gameApprovalRequestDto, GameApprovalRequestDto.class);

        //Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // Ensure it's created

        GameApprovalRequestDto response = responseEntity.getBody();
        assertNotNull(response);  // Check if response body is not null
    }

    @Test
    @Order(2)
    public void testGetGameApprovalRequestById(){
        //Arrange
        GameApprovalRequestDto gameApprovalRequestDto = new GameApprovalRequestDto(
            "Test Game",
            "A test game for approval",
            "Action",
            "image.png",
            Status.PendingApproval,
            employeeId,
            managerId
        );

        ResponseEntity<GameApprovalRequestDto> responseEntity = client.postForEntity("/gameapproval", gameApprovalRequestDto, GameApprovalRequestDto.class);
        GameApprovalRequestDto response = responseEntity.getBody();
        gameApprovalRequestId = response.getId();  // Capture ID after saving

        //Act
        ResponseEntity<GameApprovalRequestDto> getResponseEntity = client.getForEntity("/gameapproval/" + gameApprovalRequestId, GameApprovalRequestDto.class);

        //Assert
        assertNotNull(getResponseEntity);
        assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());  // Ensure it's OK
        GameApprovalRequestDto getResponse = getResponseEntity.getBody();
        assertNotNull(getResponse);  // Check if response body is not null
    }

    @Test
    @Order(3)
    public void testUpdateGameApprovalRequest(){
        //Arrange
        GameApprovalRequestDto gameApprovalRequestDto = new GameApprovalRequestDto(
            "Test Game",
            "A test game for approval",
            "Action",
            "image.png",
            Status.PendingApproval,
            employeeId,
            managerId
        );

        ResponseEntity<GameApprovalRequestDto> responseEntity = client.postForEntity("/gameapproval", gameApprovalRequestDto, GameApprovalRequestDto.class);
        GameApprovalRequestDto response = responseEntity.getBody();
        gameApprovalRequestId = response.getId();  // Capture ID after saving

        //Act
        GameApprovalRequestDto updatedGameApprovalRequestDto = new GameApprovalRequestDto(
            "Updated Game",
            "An updated test game for approval",
            "Action",
            "image.png",
            Status.Approved,
            employeeId,
            managerId
        );

        // Send PUT request for updating
        ResponseEntity<GameApprovalRequestDto> updateResponseEntity = client.exchange(
            "/gameapproval/" + gameApprovalRequestId,
            HttpMethod.PUT,
            new HttpEntity<>(updatedGameApprovalRequestDto),
            GameApprovalRequestDto.class
        );

        // Assert
        assertNotNull(updateResponseEntity);
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());  // Ensure it's OK
        GameApprovalRequestDto updateResponse = updateResponseEntity.getBody();
        assertNotNull(updateResponse);  // Check if response body is not null
        assertEquals("Updated Game", updateResponse.getName());
        assertEquals("An updated test game for approval", updateResponse.getDescription());
        assertEquals(Status.Approved, updateResponse.getStatus());
    }

    @Test
    @Order(4)
    public void testDeleteGameApprovalRequest(){
        //Arrange
        GameApprovalRequestDto gameApprovalRequestDto = new GameApprovalRequestDto(
            "Test Game",
            "A test game for approval",
            "Action",
            "image.png",
            Status.PendingApproval,
            employeeId,
            managerId
        );

        ResponseEntity<GameApprovalRequestDto> responseEntity = client.postForEntity("/gameapproval", gameApprovalRequestDto, GameApprovalRequestDto.class);
        GameApprovalRequestDto response = responseEntity.getBody();
        gameApprovalRequestId = response.getId();  // Capture ID after saving

        //Act
        ResponseEntity<?> deleteResponseEntity = client.exchange(
            "/gameapproval/" + gameApprovalRequestId,
            HttpMethod.DELETE,
            null,
            Object.class
        );

        //Assert
        assertNotNull(deleteResponseEntity);
        assertEquals(HttpStatus.OK, deleteResponseEntity.getStatusCode());  // Ensure it's OK
    }

    @Test
    @Order(5)
    public void testGetAllGameApprovalRequests(){
        //Arrange
        GameApprovalRequestDto gameApprovalRequestDto = new GameApprovalRequestDto(
            "Test Game",
            "A test game for approval",
            "Action",
            "image.png",
            Status.PendingApproval,
            employeeId,
            managerId
        );

        ResponseEntity<GameApprovalRequestDto> responseEntity = client.postForEntity("/gameapproval", gameApprovalRequestDto, GameApprovalRequestDto.class);
        GameApprovalRequestDto response = responseEntity.getBody();
        gameApprovalRequestId = response.getId();  // Capture ID after saving

        //Act
        ResponseEntity<GameApprovalRequestDto[]> getResponseEntity = client.getForEntity("/gameapproval", GameApprovalRequestDto[].class);

        //Assert
        assertNotNull(getResponseEntity);
        assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());  // Ensure it's OK
        GameApprovalRequestDto[] getResponse = getResponseEntity.getBody();
        assertNotNull(getResponse);  // Check if response body is not null
        assertTrue(getResponse.length > 0);  // Check if response body is not empty
    }
}
