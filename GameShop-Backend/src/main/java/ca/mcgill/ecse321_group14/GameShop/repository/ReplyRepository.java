package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Integer> {
    Reply findReplyById(int id);
    List<Reply> findAll();
}
