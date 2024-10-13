package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GameApprovalRequestRepositoryTest {
    @Autowired
    private GameApprovalRequestRepository gameApprovalRequestRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    @AfterEach
    public void ClearDatabase(){
        gameApprovalRequestRepository.deleteAll();
        managerRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPromotion(){
        Manager manager = new Manager("password", "email", "username");
        manager = managerRepository.save(manager);
        Employee employee = new Employee("employeepass", "employ@mcgill.ca", "useremployee");
        employee = employeeRepository.save(employee);
        GameApprovalRequest gameApprovalRequest = new GameApprovalRequest("name", "description","category", "picture", null, employee, manager );
        gameApprovalRequest = gameApprovalRequestRepository.save(gameApprovalRequest);

        int id = gameApprovalRequest.getId();

        GameApprovalRequest readGameApprovalRequest = gameApprovalRequestRepository.findGameApprovalRequestById(id);
        assertNotNull(readGameApprovalRequest);
        assertEquals(gameApprovalRequest.getId(), readGameApprovalRequest.getId());
        assertEquals(gameApprovalRequest.getRequestCreator().getId(), readGameApprovalRequest.getRequestCreator().getId());
        assertEquals(gameApprovalRequest.getRequestApprover().getId(), readGameApprovalRequest.getRequestApprover().getId());
    }


}
