package ca.mcgill.ecse321_group14.GameShop.integration;

import java.sql.Date;

import ca.mcgill.ecse321_group14.GameShop.dto.*;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PersonRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PolicyRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PolicyIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PolicyRepository policyRepository;
    private static final String Description = "Policy description";


    @AfterAll
    public void clearDatabase() {
        policyRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidPolicy() {
        // Arrange
        ResponseEntity<PolicyResponseDto> response = client.postForEntity("/policy", new PolicyRequestDto(Description), PolicyResponseDto.class);

        // Act
        PolicyResponseDto createdPolicy = response.getBody();

        // Assert
        assertNotNull(createdPolicy);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Description, createdPolicy.getDescription());
    }

    @Test
    @Order(2)
    public void testCreateInvalidPolicy() {
        // Act
        ResponseEntity<String> response = client.postForEntity("/policy", new PolicyRequestDto(null), String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Expecting 400 Bad Request

    }

    @Test
    @Order(3)
    public void testGetPolicy() {
        // Arrange
        ResponseEntity<PolicyResponseDto> response = client.postForEntity("/policy", new PolicyRequestDto(Description), PolicyResponseDto.class);
        PolicyResponseDto createdPolicy = response.getBody();

        // Act

        Integer foundPolicyId = createdPolicy.getPolicyId();
        ResponseEntity<PolicyResponseDto> getResponse = client.getForEntity("/policy/" + foundPolicyId, PolicyResponseDto.class);
        // Assert
        assertNotNull(getResponse);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(Description, getResponse.getBody().getDescription());
    }

    @Test
    @Order(4)
    public void testGetNonExistentPolicy() {
        // Act
        ResponseEntity<String> response = client.getForEntity("/policy/0", String.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Expecting 400 Bad Request
    }

    @Test
    @Order(5)
    public void testUpdatePolicy() {
        // Arrange
        String initialDescription = "Initial policy description";
        String updatedDescription = "Updated policy description";

        // Create the policy
        ResponseEntity<PolicyResponseDto> createResponse = client.postForEntity("/policy", new PolicyRequestDto(initialDescription), PolicyResponseDto.class);
        PolicyResponseDto createdPolicy = createResponse.getBody();
        assertNotNull(createdPolicy);
        Integer foundPolicyId = createdPolicy.getPolicyId();

        // Act - Update the policy with a new description
        ResponseEntity<PolicyResponseDto> updateResponse = client.postForEntity("/policy/update/" + foundPolicyId, new PolicyRequestDto(updatedDescription), PolicyResponseDto.class);
        PolicyResponseDto updatedPolicy = updateResponse.getBody();

        // Assert
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode()); // Ensure update was successful
        assertNotNull(updatedPolicy);
        assertEquals(foundPolicyId, updatedPolicy.getPolicyId()); // Check ID is the same
        assertEquals(updatedDescription, updatedPolicy.getDescription()); // Check if description is updated
    }

    @Test
    @Order(6)
    public void testUpdateNonExistentPolicy() {
        // Act
        ResponseEntity<String> response = client.postForEntity("/policy/update/0", new PolicyRequestDto("Updated description"), String.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Expecting 404 Not Found
    }

    @Test
    @Order(7)
    public void testUpdatePolicyInvalidDescription() {
        // Arrange
        String initialDescription = "Initial policy description";
        String updatedDescription = null;

        // Create the policy
        ResponseEntity<PolicyResponseDto> createResponse = client.postForEntity("/policy", new PolicyRequestDto(initialDescription), PolicyResponseDto.class);
        PolicyResponseDto createdPolicy = createResponse.getBody();
        assertNotNull(createdPolicy);
        Integer foundPolicyId = createdPolicy.getPolicyId();

        // Act - Update the policy with a new description
        ResponseEntity<PolicyResponseDto> updateResponse = client.postForEntity("/policy/update/" + foundPolicyId, new PolicyRequestDto(updatedDescription), PolicyResponseDto.class);
        PolicyResponseDto updatedPolicy = updateResponse.getBody();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode()); // Expecting 400 Bad Request
    }

    @Test
    @Order(8)
    public void testDeletePolicy() {
        // Arrange
        ResponseEntity<PolicyResponseDto> createResponse = client.postForEntity("/policy", new PolicyRequestDto(Description), PolicyResponseDto.class);
        PolicyResponseDto createdPolicy = createResponse.getBody();
        assertNotNull(createdPolicy);
        Integer foundPolicyId = createdPolicy.getPolicyId();

        // Act
        client.delete("/policy/" + foundPolicyId);

        // Assert
        ResponseEntity<String> getResponse = client.getForEntity("/policy/" + foundPolicyId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());

    }

    @Test
    @Order(9)
    public void testDeleteNonExistentPolicy() {
        // Act
        ResponseEntity<String> response = client.postForEntity("/policy/delete/0", new PolicyRequestDto("Updated description"), String.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Expecting 404 Not Found
    }

    @Test
    @Order(10)
    public void testDeletePolicyInvalidId() {
        // Act
        ResponseEntity<String> response = client.postForEntity("/policy/delete/-1", new PolicyRequestDto("Updated description"), String.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Expecting 404 Not Found
    }




}
