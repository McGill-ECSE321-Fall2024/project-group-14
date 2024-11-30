package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.GameApprovalRequestDto;
import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest;
import ca.mcgill.ecse321_group14.GameShop.service.GameApprovalService;
import ca.mcgill.ecse321_group14.GameShop.service.EmployeeService;
import ca.mcgill.ecse321_group14.GameShop.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
public class GameApprovalRequestController {

    @Autowired
    private GameApprovalService gameApprovalService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ManagerService managerService;

    /**
     * Create a new game approval request.
     */
    @PostMapping("/gameapproval")
    public ResponseEntity<GameApprovalRequestDto> createGameApprovalRequest(@RequestBody GameApprovalRequestDto gameApprovalRequestDto) {
        try {
            if (gameApprovalRequestDto.getStatus() == null) {
                gameApprovalRequestDto.setStatus(GameApprovalRequest.Status.PendingApproval); // Default to PendingApproval
            }
            GameApprovalRequest createdRequest = gameApprovalService.createGameApprovalRequest(
                gameApprovalRequestDto.getName(),
                gameApprovalRequestDto.getDescription(),
                gameApprovalRequestDto.getCategory(),
                gameApprovalRequestDto.getPicture(),
                gameApprovalRequestDto.getStatus(),
                employeeService.getEmployeeById(gameApprovalRequestDto.getRequestCreatorId()),
                managerService.getManagerById(gameApprovalRequestDto.getRequestApproverId())
            );
            return new ResponseEntity<>(new GameApprovalRequestDto(
                createdRequest.getId(),
                createdRequest.getName(),
                createdRequest.getDescription(),
                createdRequest.getCategory(),
                createdRequest.getPicture(),
                createdRequest.getStatus(),
                createdRequest.getRequestCreator().getId(),
                createdRequest.getRequestApprover().getId()
            ), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get a specific game approval request by ID.
     */
    @GetMapping("/gameapproval/{id}")
    public ResponseEntity<GameApprovalRequestDto> getGameApprovalRequestById(@PathVariable int id) {
        try {
            GameApprovalRequest request = gameApprovalService.getGameApprovalRequest(id);
            return new ResponseEntity<>(new GameApprovalRequestDto(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getCategory(),
                request.getPicture(),
                request.getStatus(),
                request.getRequestCreator().getId(),
                request.getRequestApprover().getId()
            ), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update an existing game approval request by ID.
     */
    @PutMapping("/gameapproval/{id}")
    public ResponseEntity<GameApprovalRequestDto> updateGameApprovalRequest(
        @PathVariable int id, @RequestBody GameApprovalRequestDto gameApprovalRequestDto) {
        try {
            GameApprovalRequest updatedRequest = gameApprovalService.updateGameApprovalRequest(
                id,
                gameApprovalRequestDto.getName(),
                gameApprovalRequestDto.getDescription(),
                gameApprovalRequestDto.getCategory(),
                gameApprovalRequestDto.getPicture(),
                gameApprovalRequestDto.getStatus(),
                employeeService.getEmployeeById(gameApprovalRequestDto.getRequestCreatorId()),
                managerService.getManagerById(gameApprovalRequestDto.getRequestApproverId())
            );
            return new ResponseEntity<>(new GameApprovalRequestDto(
                updatedRequest.getId(),
                updatedRequest.getName(),
                updatedRequest.getDescription(),
                updatedRequest.getCategory(),
                updatedRequest.getPicture(),
                updatedRequest.getStatus(),
                updatedRequest.getRequestCreator().getId(),
                updatedRequest.getRequestApprover().getId()
            ), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a game approval request by ID.
     */
    @DeleteMapping("/gameapproval/{id}")
    public ResponseEntity<?> deleteGameApprovalRequest(@PathVariable int id) {
        try {
            gameApprovalService.deleteGameApprovalRequest(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all game approval requests.
     */
    @GetMapping("/gameapproval")
    public ResponseEntity<List<GameApprovalRequestDto>> getAllGameApprovalRequests() {
        List<GameApprovalRequest> requests = (List<GameApprovalRequest>) gameApprovalService.getAllGameApprovalRequests();
        List<GameApprovalRequestDto> requestDtos = requests.stream()
            .map(request -> new GameApprovalRequestDto(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getCategory(),
                request.getPicture(),
                request.getStatus(),
                request.getRequestCreator().getId(),
                request.getRequestApprover().getId()
            ))
            .collect(Collectors.toList());
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }
}
