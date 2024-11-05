import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameApprovalRequestRepository;

@Service
public class GameApprovalService {
    
    @Autowired
    private GameApprovalRequestRepository gameApprovalRequestRepository;

    @Transactional
    public GameApprovalRequest createGameApprovalRequest(String aName, String aDescription, String aCategory, String aPicture, GameApprovalRequest.Status aStatus, Employee aRequestCreator, Manager aRequestApprover) {
        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Game name cannot be empty!");
        }
        if (aDescription == null || aDescription.trim().length() == 0) {
            throw new IllegalArgumentException("Game description cannot be empty!");
        }
        if (aCategory == null || aCategory.trim().length() == 0) {
            throw new IllegalArgumentException("Game category cannot be empty!");
        }
        if (aPicture == null || aPicture.trim().length() == 0) {
            throw new IllegalArgumentException("Game picture cannot be empty!");
        }
        if (aStatus == null) {
            throw new IllegalArgumentException("Game status cannot be empty!");
        }
        if (aRequestCreator == null) {
            throw new IllegalArgumentException("Game request creator cannot be empty!");
        }
        if (aRequestApprover == null) {
            throw new IllegalArgumentException("Game request approver cannot be empty!");
        }
        GameApprovalRequest gameApprovalRequest = new GameApprovalRequest(aName, aDescription, aCategory, aPicture, aStatus, aRequestCreator, aRequestApprover);
        return gameApprovalRequest;
    }

    @Transactional
    public GameApprovalRequest getGameApprovalRequest(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID cannot be empty!");
        }
        GameApprovalRequest gameApprovalRequest = gameApprovalRequestRepository.findGameApprovalRequestById(id);
        return gameApprovalRequest;
    }

    @Transactional
    public GameApprovalRequest updateGameApprovalRequest(int id, String aName, String aDescription, String aCategory, String aPicture, GameApprovalRequest.Status aStatus, Employee aRequestCreator, Manager aRequestApprover) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID cannot be empty!");
        }
        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Game name cannot be empty!");
        }
        if (aDescription == null || aDescription.trim().length() == 0) {
            throw new IllegalArgumentException("Game description cannot be empty!");
        }
        if (aCategory == null || aCategory.trim().length() == 0) {
            throw new IllegalArgumentException("Game category cannot be empty!");
        }
        if (aPicture == null || aPicture.trim().length() == 0) {
            throw new IllegalArgumentException("Game picture cannot be empty!");
        }
        if (aStatus == null) {
            throw new IllegalArgumentException("Game status cannot be empty!");
        }
        if (aRequestCreator == null) {
            throw new IllegalArgumentException("Game request creator cannot be empty!");
        }
        if (aRequestApprover == null) {
            throw new IllegalArgumentException("Game request approver cannot be empty!");
        }
        GameApprovalRequest gameApprovalRequest = gameApprovalRequestRepository.findGameApprovalRequestById(id);
        gameApprovalRequest.setName(aName);
        gameApprovalRequest.setDescription(aDescription);
        gameApprovalRequest.setCategory(aCategory);
        gameApprovalRequest.setPicture(aPicture);
        gameApprovalRequest.setStatus(aStatus);
        gameApprovalRequest.setRequestCreator(aRequestCreator);
        gameApprovalRequest.setRequestApprover(aRequestApprover);
        return gameApprovalRequest;
    }

    @Transactional
    public void deleteGameApprovalRequest(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID cannot be empty!");
        }
        GameApprovalRequest gameApprovalRequest = gameApprovalRequestRepository.findGameApprovalRequestById(id);
        gameApprovalRequestRepository.delete(gameApprovalRequest);
    }

    @Transactional
    public Iterable<GameApprovalRequest> getAllGameApprovalRequests() {
        return gameApprovalRequestRepository.findAll();
    }

    

    
}
