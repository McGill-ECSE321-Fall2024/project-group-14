package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerById(int id);
    
}
