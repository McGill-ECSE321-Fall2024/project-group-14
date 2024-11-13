package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ReplyResponseDto createReply(@RequestBody ReplyRequestDto replyRequestDto) {
        Reply reply = replyService.createReply(replyRequestDto.getDescription(), replyRequestDto.getReviewId(), replyRequestDto.getManagerId());
        return new ReplyResponseDto(reply);
    }


    /**
     * Get a reply by id
     * 
     * @param id
     * @return ReplyResponseDto
     */
    @GetMapping("/reply/{id}")
    public ReplyResponseDto getReply(@PathVariable int id) {
        Reply reply = replyService.getReply(id);
        return new ReplyResponseDto(reply);
    }

    /**
     * Delete a reply by id
     * 
     * @param id
     */
    @GetMapping("/reply/delete/{id}")
    public void deleteReply(@PathVariable int id) {
        replyService.deleteReply(id);
    }

}