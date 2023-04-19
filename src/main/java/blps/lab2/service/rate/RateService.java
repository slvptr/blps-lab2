package blps.lab2.service.rate;

import blps.lab2.dao.TopicRepository;
import blps.lab2.dao.UserRepository;
import blps.lab2.model.domain.topic.Topic;
import blps.lab2.model.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Service
public class RateService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RateService(TopicRepository topicRepository,
                       UserRepository userRepository, TransactionTemplate transactionTemplate) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.transactionTemplate = transactionTemplate;
    }


    public Topic rateTopic(long userId, long topicId){
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isEmpty()) return null;

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) return null;

        Topic topic = topicOptional.get();
        User user = userOptional.get();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                if (user.getRemainingGrades() > 0) {
                    user.setRemainingGrades(user.getRemainingGrades() - 1);
                    userRepository.save(user);
                    topic.setRate(topic.getRate() + 1);
                    topicRepository.save(topic);
                } else throw new RuntimeException("У пользователя нет доступных лайков");
            }
        });

        return topic;
    }
}
