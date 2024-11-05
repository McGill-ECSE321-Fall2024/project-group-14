package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;


    @Transactional
    public Reply createReply(String aDescription, Review aReview, Manager aManager) {
        if (aDescription == null || aReview == null || aManager == null) {
            throw new IllegalArgumentException("All fields must be filled!");
        }
        Reply reply = new Reply(aDescription, aReview, aManager);
        replyRepository.save(reply);
        return reply;
    }
    
    @Transactional
    public Reply getReply(int id) {
        Reply reply = replyRepository.findReplyById(id);
        if (reply == null) {
            throw new IllegalArgumentException("Reply does not exist!");
        }
        return reply;
    }

    @Transactional
    public Reply deleteReply(int id) {
        Reply reply = replyRepository.findReplyById(id);
        if (reply == null) {
            throw new IllegalArgumentException("Reply does not exist!");
        }
        replyRepository.delete(reply);
        return reply;
    }


}
