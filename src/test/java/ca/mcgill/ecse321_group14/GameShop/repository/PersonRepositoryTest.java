package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.Person;

@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {
        // Arrange
        Person Alex = new Person(123, "password", "email", "username");
        Alex = personRepository.save(Alex);
        int id = Alex.getId();

        // Act
        Person person = personRepository.findPersonById(id);

        // Assert
        assertNotNull(person);
        assertEquals(id, person.getId());
        assertEquals(Alex.getUsername(), person.getUsername());
        assertEquals(Alex.getEmail(), person.getEmail());
        assertEquals(Alex.getPassword(), person.getPassword());

    }
}
