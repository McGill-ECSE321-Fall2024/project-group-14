package ca.mcgill.ecse321_group14.GameShop.service;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import ca.mcgill.ecse321_group14.GameShop.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PolicyService {
    @Autowired
    PolicyRepository policyRepository;

    @Transactional
    public void deletePolicy(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Policy ID cannot be negative!");
        }
        if (!policyRepository.existsById(id)) {
            throw new IllegalArgumentException("Policy does not exist!");
        }
        policyRepository.deleteById(id);
    }

    @Transactional
    public Policy getPolicy(int id) {
        Policy policy = policyRepository.findPolicyByPolicyId(id);
        if (policy == null) {
            throw new IllegalArgumentException("Policy does not exist!");
        }
        return policy;
    }

    @Transactional
    public Policy updatePolicy(int id, String description) {
        Policy policy = policyRepository.findPolicyByPolicyId(id);
        if (policy == null) {
            throw new IllegalArgumentException("Policy does not exist!");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        try {
            policy.setDescription(description);
            policyRepository.save(policy);
            return policy;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Transactional
    public Policy createPolicy(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        try {
            Policy policy = new Policy(description);
            policyRepository.save(policy);
            return policy;
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while creating the policy.", e);
        }
    }
}