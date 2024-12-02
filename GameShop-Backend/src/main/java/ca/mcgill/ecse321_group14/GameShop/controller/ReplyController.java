package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321_group14.GameShop.dto.ReplyRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReplyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import ca.mcgill.ecse321_group14.GameShop.service.ReplyService;




@RestController
public class ReplyController {
    
    @Autowired
    private ReplyService replyService;

    /**
     * Create a reply
     * 
     * @param replyRequestDto
     * @return ReplyResponseDto
     */
    @PostMapping("/reply")
    public ResponseEntity<ReplyResponseDto> createReply(@RequestBody ReplyRequestDto replyRequestDto) {
        Reply reply = replyService.createReply(replyRequestDto.getDescription(), replyRequestDto.getReviewId(), replyRequestDto.getManagerId());
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

}