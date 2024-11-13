package ca.mcgill.ecse321_group14.GameShop.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;
import jakarta.transaction.Transactional;

@Service
public class ManagerService {
    
    @Autowired
    private ManagerRepository managerRepository;

    @Transactional
    public Manager createManager(String name, String email, String password) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Manager name cannot be empty.");
        }
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Manager email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Manager password cannot be empty.");
        }
        Manager manager = new Manager(password, email, name);
        managerRepository.save(manager);
        return manager;
    }

    @Transactional
    public Manager getManagerByEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Manager email cannot be empty.");
        }
        Manager manager = managerRepository.findManagerByEmail(email);
        if (manager == null) {
            throw new IllegalArgumentException("Manager not found.");
        }
        return manager;
    }

    @Transactional
    public Manager getManagerById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Manager id cannot be empty.");
        }
        Manager manager = managerRepository.findManagerById(id);
        if (manager == null) {
            throw new IllegalArgumentException("Manager not found.");
        }
        return manager;
    }

    @Transactional
    public Manager updateManager(int id, String name, String email, String password) {
        if (id == 0) {
            throw new IllegalArgumentException("Manager id cannot be empty.");
        }
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Manager name cannot be empty.");
        }
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Manager email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Manager password cannot be empty.");
        }
        Manager manager = managerRepository.findManagerById(id);
        manager.setUsername(name);
        manager.setEmail(email);
        manager.setPassword(password);
        managerRepository.save(manager);
        return manager;
    }


    @Transactional
    public boolean loginManager(String email, String password) {
        if (email == null || email.trim().length() == 0) {
            throw new IllegalArgumentException("Manager email cannot be empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Manager password cannot be empty.");
        }
        Manager manager = managerRepository.findManagerByEmail(email);
        if (manager == null) {
            throw new IllegalArgumentException("Manager not found.");
        }
        return manager.getPassword().equals(password);
    }

}
