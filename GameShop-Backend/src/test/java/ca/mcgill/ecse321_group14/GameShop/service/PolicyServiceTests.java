package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import ca.mcgill.ecse321_group14.GameShop.repository.PolicyRepository;
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
public class PolicyServiceTests {

    @Mock
    PolicyRepository policyRepository;

    @InjectMocks
    PolicyService policyService;

    @Test
    public void testCreatePolicy() {
        String description = "Policy description";
        when(policyRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Policy createdPolicy = policyService.createPolicy(description);

        assertNotNull(createdPolicy);
        assertEquals(description, createdPolicy.getDescription());
        verify(policyRepository, times(1)).save(any());
    }

    @Test
    public void testCreatePolicyNullDescription() {
        String description = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.createPolicy(description);
        });

        assertEquals("Description cannot be empty.", exception.getMessage());
        verify(policyRepository, times(0)).save(any());
    }

    @Test
    public void testGetPolicy() {
        int id = 1;
        Policy policy = new Policy("Policy description");
        when(policyRepository.findPolicyByPolicyId(id)).thenReturn(policy);

        Policy foundPolicy = policyService.getPolicy(id);

        assertNotNull(foundPolicy);
        assertEquals(policy, foundPolicy);
        verify(policyRepository, times(1)).findPolicyByPolicyId(id);
    }

    @Test
    public void testGetPolicyNonExistentId() {
        int id = 1;
        when(policyRepository.findPolicyByPolicyId(id)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.getPolicy(id);
        });

        assertEquals("Policy does not exist!", exception.getMessage());
        verify(policyRepository, times(1)).findPolicyByPolicyId(id);
    }

    @Test
    public void testUpdatePolicy() {
        int id = 1;
        String description = "Updated description";
        Policy policy = new Policy("Old description");
        when(policyRepository.findPolicyByPolicyId(id)).thenReturn(policy);
        when(policyRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Policy updatedPolicy = policyService.updatePolicy(id, description);

        assertNotNull(updatedPolicy);
        assertEquals(description, updatedPolicy.getDescription());
        verify(policyRepository, times(1)).findPolicyByPolicyId(id);
        verify(policyRepository, times(1)).save(any());
    }

    @Test
    public void testUpdatePolicyNonExistentId() {
        int id = 1;
        String description = "Updated description";
        when(policyRepository.findPolicyByPolicyId(id)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.updatePolicy(id, description);
        });

        assertEquals("Policy does not exist!", exception.getMessage());
        verify(policyRepository, times(1)).findPolicyByPolicyId(id);
        verify(policyRepository, times(0)).save(any());
    }

    @Test
    public void testDeletePolicy() {
        int id = 1;
        when(policyRepository.existsById(id)).thenReturn(true);

        policyService.deletePolicy(id);

        verify(policyRepository, times(1)).existsById(id);
        verify(policyRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletePolicyNonExistentId() {
        int id = 1;
        when(policyRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.deletePolicy(id);
        });

        assertEquals("Policy does not exist!", exception.getMessage());
        verify(policyRepository, times(1)).existsById(id);
        verify(policyRepository, times(0)).deleteById(id);
    }
}