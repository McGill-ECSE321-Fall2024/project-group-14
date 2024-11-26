package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(int id);
    
    Employee findEmployeeByEmail(String email);

}
