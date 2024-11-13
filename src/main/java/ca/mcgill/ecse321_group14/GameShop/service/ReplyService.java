package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired 
    private ManagerRepository managerRepository;


    @Transactional
    public Reply createReply(String aDescription, int reviewId, int managerId) {

        if (aDescription == null){
            throw new IllegalArgumentException("Description cannot be empty!");
        }

        Review aReview = reviewRepository.findReviewById(reviewId);
        
        if (aReview == null){
            throw new IllegalArgumentException("Review does not exist!");
        }

        Manager aManager = managerRepository.findManagerById(managerId);

        if (aManager == null){
            throw new IllegalArgumentException("Manager does not exist!");
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
