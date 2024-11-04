package ca.mcgill.ecse321_group14.GameShop.repository;

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
    
    @BeforeEach
    @AfterEach
    public void ClearDatabase(){
        policyRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPolicy(){
        Policy policy = new Policy("description");
        policy = policyRepository.save(policy);

        int id = policy.getPolicyId();

        Policy readPolicy = policyRepository.findPolicyByPolicyId(id);

        assertNotNull(readPolicy);
        assertEquals(policy.getPolicyId(),readPolicy.getPolicyId());
        assertEquals(policy.getDescription(), readPolicy.getDescription());
        
    }

}
