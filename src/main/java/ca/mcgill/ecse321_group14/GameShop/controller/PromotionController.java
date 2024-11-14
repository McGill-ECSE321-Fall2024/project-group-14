package  ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.PromotionRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PromotionResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.PromotionService;

@RestController
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private GameService gameService;

    /**
     * Create a promotion
     * 
     * @param gameId
     * @param description
     * @param discount
     * @return PromotionResponseDto
     */
    @PutMapping("/promotion/{gameId}")
    public PromotionResponseDto createPromotion(@RequestBody PromotionRequestDto promotionRequestDto , @PathVariable int gameId) {
        Game game = gameService.getGameById(gameId);
        Promotion promotion = promotionService.createPromotion(promotionRequestDto.getDescription(), promotionRequestDto.getDiscount(), game);
        return new PromotionResponseDto(promotion);
    }

    /**
     * Delete a promotion
     * 
     * @param id
     */
    @DeleteMapping("/promotion/{id}")
public ResponseEntity<Void> deletePromotion(@PathVariable int id) {
    if (promotionService.getPromotion(id) == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
    }
    promotionService.deletePromotion(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content on success
}

    /**
     * Get a promotion
     * 
     * @param id
     * @return PromotionResponseDto
     */

    @GetMapping("/promotion/{id}")
    public PromotionResponseDto getPromotion(@PathVariable int id) {
        Promotion promotion = promotionService.getPromotion(id);
        return new PromotionResponseDto(promotion);
    }

    /**
     * Update a promotion
     * 
     * @param id
     * @param description
     * @param discount
     * @param gameId
     * @return PromotionResponseDto
     */

     @PutMapping("/promotion/update/{id}")
     public PromotionResponseDto updatePromotion(@RequestBody PromotionRequestDto promotionRequestDto, @PathVariable int id) {
         Game game = null;
         if (promotionRequestDto.getGameId() != 0) { // Check if gameId is provided (0 indicates no game association)
             game = gameService.getGameById(promotionRequestDto.getGameId());
             if (game == null) {
                 throw new IllegalArgumentException("Game does not exist!");
             }
         }
         Promotion promotion = promotionService.updatePromotion(id, promotionRequestDto.getDescription(), promotionRequestDto.getDiscount(), game);
         return new PromotionResponseDto(promotion);
     }
     
     @ExceptionHandler(IllegalArgumentException.class)
     @ResponseStatus(HttpStatus.NOT_FOUND)
     public ResponseEntity<String> handleNotFoundException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
