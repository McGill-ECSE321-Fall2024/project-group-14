package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;


public class PromotionListDtos {
    // Default constructor
    public PromotionListDtos() {
    }

    private List<PromotionResponseDto> promotions;

    public PromotionListDtos(List<PromotionResponseDto> promotions) {
        this.promotions = promotions;
    }

    public List<PromotionResponseDto> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionResponseDto> promotions) {
        this.promotions = promotions;
    }
}
