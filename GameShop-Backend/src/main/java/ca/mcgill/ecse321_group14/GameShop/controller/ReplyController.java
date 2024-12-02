package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321_group14.GameShop.dto.ReplyRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReplyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import ca.mcgill.ecse321_group14.GameShop.service.ReplyService;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply")
    public ResponseEntity<ReplyResponseDto> createReply(@RequestBody ReplyRequestDto replyRequestDto) {

        String description = replyRequestDto.getDescription();
        int id = replyRequestDto.getManagerId();
        int reviewId = replyRequestDto.getReviewId();
        // Proceed with original functionality
        Reply reply = replyService.createReply(description, reviewId, id);
        return new ResponseEntity<>(new ReplyResponseDto(reply), HttpStatus.CREATED);
    }

    /**
     * Get a reply by id
     * 
     * @param id
     * @return ReplyResponseDto
     */
    @GetMapping("/reply/{id}")
    public ResponseEntity<ReplyResponseDto> getReply(@PathVariable int id) {
        Reply reply = replyService.getReply(id);
        return new ResponseEntity<>(new ReplyResponseDto(reply), HttpStatus.OK);
    }

    /**
     * Delete a reply by id
     * 
     * @param id
     */
    @GetMapping("/reply/delete/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable int id) {

        replyService.deleteReply(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get all replies
     */
    @GetMapping("/replies")
    public List<ReplyResponseDto> getAllReplies() {
        List<ReplyResponseDto> dtos = new ArrayList<>();
        for (Reply reply : replyService.getAllReplies()) {
            dtos.add(new ReplyResponseDto(reply));
        }

        return dtos;
    }

}