package ca.mcgill.ecse321_group14.GameShop.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Manager findManagerById(int id);

    Manager findManagerByEmail(String email);

}
