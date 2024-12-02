package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonById(int id);
 
}
