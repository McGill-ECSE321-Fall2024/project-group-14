package ca.mcgill.ecse321_group14.GameShop.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;

@SpringBootTest
public class PolicyRepositoryTest {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        policyRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPolicy() {
        // Arrange
        Manager manager = new Manager(null, null, null); // create a new manager
        manager = managerRepository.save(manager); // save the manager to the database

        Policy policy = new Policy("No return for onsale items", manager); // create a new policy
        policy = policyRepository.save(policy); // save the policy to the database
        int id = policy.getPolicyId(); // get the id of the policy

        // Act
        Policy readPolicy = policyRepository.findPolicyByPolicyId(id); // read the policy from the database

        // Assert
        assertNotNull(readPolicy);
        assertEquals(policy.getPolicyId(), readPolicy.getPolicyId());
        assertEquals(policy.getDescription(), readPolicy.getDescription());
        assertEquals(policy.getManager(), readPolicy.getManager());
        
    }
}
