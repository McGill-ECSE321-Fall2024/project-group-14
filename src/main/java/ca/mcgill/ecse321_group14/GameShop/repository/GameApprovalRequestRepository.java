package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest;
import org.springframework.data.repository.CrudRepository;

public interface GameApprovalRequestRepository extends CrudRepository<GameApprovalRequest, Integer> {
    GameApprovalRequest findGameApprovalRequestById(int id);
}
