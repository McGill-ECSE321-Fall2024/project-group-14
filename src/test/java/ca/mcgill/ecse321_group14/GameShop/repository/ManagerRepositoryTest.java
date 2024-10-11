package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ManagerRepositoryTest {
    @Autowired
    private ManagerRepository managerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        managerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadManager() {
        Manager manager = new Manager("password", "easy@mcgill.ca", "testManager");
        managerRepository.save(manager);
        int id = manager.getId();

        Manager readManager = managerRepository.findManagerById(id);
        assertNotNull(readManager);
        assertEquals(manager.getId(), readManager.getId());
    }
}
