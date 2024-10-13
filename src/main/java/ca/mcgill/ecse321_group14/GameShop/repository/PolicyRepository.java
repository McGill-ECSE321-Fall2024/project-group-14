package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import org.springframework.data.repository.CrudRepository;

public interface PolicyRepository extends CrudRepository<Policy, Integer> {
    Policy findPolicyByPolicyId(int id);
}
