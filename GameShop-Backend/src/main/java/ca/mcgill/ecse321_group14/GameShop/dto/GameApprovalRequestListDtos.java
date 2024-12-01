package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;


public class GameApprovalRequestListDtos {
    
    public GameApprovalRequestListDtos() {
    }

    private List<GameApprovalRequestDto> gameApprovalRequests;

    public GameApprovalRequestListDtos(List<GameApprovalRequestDto> gameApprovalRequests) {
        this.gameApprovalRequests = gameApprovalRequests;
    }

    public List<GameApprovalRequestDto> getGameApprovalRequests() {
        return gameApprovalRequests;
    }

    public void setGameApprovalRequests(List<GameApprovalRequestDto> gameApprovalRequests) {
        this.gameApprovalRequests = gameApprovalRequests;
    }
}
