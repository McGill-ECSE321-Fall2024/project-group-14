package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;

public interface PolicyRepository extends CrudRepository<Policy, Integer> {
    Policy findPolicyByPolicyId(int policyId);
    
}
