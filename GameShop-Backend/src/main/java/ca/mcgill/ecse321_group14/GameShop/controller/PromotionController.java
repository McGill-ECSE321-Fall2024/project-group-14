package ca.mcgill.ecse321_group14.GameShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.PromotionRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PromotionResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.PromotionService;
import ca.mcgill.ecse321_group14.GameShop.dto.PromotionListDtos;
import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/promotion")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private GameService gameService;

    /**
     * Create a promotion
     *
     * @param gameId
     * @param promotionRequestDto
     * @return ResponseEntity<PromotionResponseDto>
     */
    @PutMapping("/{gameId}")
    public ResponseEntity<PromotionResponseDto> createPromotion(
            @PathVariable int gameId,
            @RequestBody PromotionRequestDto promotionRequestDto) {
        try {
            Promotion promotion = promotionService.createPromotion(
                promotionRequestDto.getDescription(),
                promotionRequestDto.getDiscount(),
                gameService.getGameById(gameId)
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new PromotionResponseDto(promotion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Delete a promotion
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable int id) {
        try {
            promotionService.deletePromotion(id);;
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * Get a promotion
     *
     * @param id
     * @return ResponseEntity<PromotionResponseDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> getPromotion(@PathVariable int id) {
        try {
            Promotion promotion = promotionService.getPromotion(id);
            return ResponseEntity.ok(new PromotionResponseDto(promotion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * Update a promotion
     *
     * @param id
     * @param promotionRequestDto
     * @return ResponseEntity<PromotionResponseDto>
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PromotionResponseDto> updatePromotion(
            @PathVariable int id,
            @RequestBody PromotionRequestDto promotionRequestDto) {
        try {
            Game game = null;
            if (promotionRequestDto.getGameId() != 0) {
                game = gameService.getGameById(promotionRequestDto.getGameId());
                if (game == null) {
                    throw new IllegalArgumentException("Game does not exist!");
                }
            }
            Promotion promotion = promotionService.updatePromotion(
                id,
                promotionRequestDto.getDescription(),
                promotionRequestDto.getDiscount(),
                game
            );
            return ResponseEntity.ok(new PromotionResponseDto(promotion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Handle IllegalArgumentException
     *
     * @param ex
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNotFoundException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @GetMapping("/promotions")
    public PromotionListDtos getAllPromotions(){
        List<PromotionResponseDto> dtos = new ArrayList<PromotionResponseDto>();
        for (Promotion promotion : promotionService.getAllPromotions()) {
            dtos.add(new PromotionResponseDto(promotion));
        }
        return new PromotionListDtos(dtos);
    }
}
