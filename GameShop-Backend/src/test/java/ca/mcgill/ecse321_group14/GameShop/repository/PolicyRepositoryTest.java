package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class PolicyRepositoryTest {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ManagerRepository managerRepository;
    
    @BeforeEach
    @AfterEach
    public void ClearDatabase(){
        policyRepository.deleteAll();
        managerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPolicy(){
        Manager manager = new Manager("password", "username", "email");
        manager = managerRepository.save(manager);
        Policy policy = new Policy("description", manager);
        policy = policyRepository.save(policy);

        int id = policy.getPolicyId();

        Policy readPolicy = policyRepository.findPolicyByPolicyId(id);

        assertNotNull(readPolicy);
        assertEquals(policy.getPolicyId(),readPolicy.getPolicyId());
        assertEquals(policy.getManager().getId(), readPolicy.getManager().getId());
        assertEquals(policy.getDescription(), readPolicy.getDescription());
        
    }

}
